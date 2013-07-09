package com.ralibi.dodombaan.component;

import java.util.List;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class ScrollMenuEntity extends Entity {

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------

  private ClippingEntity clippingEntity;

  protected float ratio = ResourcesManager.getInstance().camera.getSurfaceHeight() / ResourcesManager.getInstance().camera.getHeight();
  private float mTouchX = 0, mTouchOffsetX = 0;
  // private float mTouchY = 0, mTouchOffsetY = 0;

  private GestureDetector mDetector;
  private ScrollPanel scrollPanel;

  // private Text selectedIconText;
  private ButtonSprite selectButton;
  private ButtonSprite navLeftButton;
  private ButtonSprite navRightButton;

  private DeselectListener deselectListener;
  
  private boolean autoselect = false;

  public ScrollMenuEntity(float x, float y, int width, int height, Sprite backgroundSprite, Scene pScene, DeselectListener deselectListener) {
    super(x, y, width, height);
    if(backgroundSprite != null){
      pScene.attachChild(backgroundSprite);
      backgroundSprite.setPosition(x, y);
    }
    clippingEntity = new ClippingEntity(x, y, width, height) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        mDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());

        updateNavigationButtonState();
        
        switch (pSceneTouchEvent.getAction()) {
        case TouchEvent.ACTION_MOVE: {
          scrollPanel.setTouching(true);
          float newX = pSceneTouchEvent.getMotionEvent().getX();
          mTouchOffsetX = (newX - mTouchX) / ratio;
          mTouchX = newX;
          
          float x = scrollPanel.getX() + mTouchOffsetX;
          float paddingLeft = (clippingEntity.getWidth() - scrollPanel.getItemWidth()) / 2;

          if (x > paddingLeft) {
            x = paddingLeft;
          } else if (x < -scrollPanel.getItemWidth() * (scrollPanel.getItemCount() - 1) + paddingLeft) {
            x = -scrollPanel.getItemWidth() * (scrollPanel.getItemCount() - 1) + paddingLeft;
          }

          scrollPanel.setPosition(x, scrollPanel.getY());

          deselectMenu();

          return true;
        }
        case TouchEvent.ACTION_DOWN: {
          scrollPanel.setTouching(true);
          scrollPanel.getmPhysicsHandler().setVelocityX(0);
          scrollPanel.getmPhysicsHandler().setAccelerationX(0);
          mTouchX = pSceneTouchEvent.getMotionEvent().getX();
          
          return false;
        }
        case TouchEvent.ACTION_UP: {
          scrollPanel.setTouching(false);

          return true;
        }
        default: {
          scrollPanel.setTouching(false);
          return true;
        }
        }
        
      }
    };
    scrollPanel = new ScrollPanel(width, height);
    clippingEntity.attachChild(scrollPanel);
    pScene.attachChild(clippingEntity);
    pScene.registerTouchArea(clippingEntity);

    this.deselectListener = deselectListener;
    // this.mDetector = new GestureDetector(new SimpleOnGestureListener());
  }

  // ---------------------------------------------
  // GETTERS SETTERS
  // ---------------------------------------------

  public ScrollPanel getScrollPanel() {
    return scrollPanel;
  }

  public void setScrollPanel(ScrollPanel scrollPanel) {
    this.scrollPanel = scrollPanel;
  }

  public int getSelectedMenuIndex() {
    return this.scrollPanel.getSelectedIndex();
  }

  public ButtonSprite getSelectButton() {
    return selectButton;
  }
  
  public void setAutoselect(boolean autoselect) {
    this.autoselect = autoselect;
    selectButton.setVisible(!autoselect);
  }

  // ---------------------------------------------
  // METHODS
  // ---------------------------------------------

  // ---------------------------------------------
  // OVERRIDES
  // ---------------------------------------------

  private void deselectMenu() {
    scrollPanel.setSelectedIndex(-1);
    // selectedIconText.setVisible(false);
    selectButton.setEnabled(true);
    
    updateNavigationButtonState();
    deselectListener.onDeselect();
  } 

  public void selectMenu(int index) {
    scrollPanel.setSelectedIndex(index);
    if(!autoselect){
      // selectedIconText.setVisible(true);
      selectButton.setEnabled(false);
    }
    updateNavigationButtonState();
    deselectListener.onSelect();
  }

  public void buildSprite(int textureX, int textureY, int textureWidth, int textureHeight, List<ITextureRegion> textureRegions, Scene pScene, VertexBufferObjectManager vbom) {
    scrollPanel.setItemCount(textureRegions.size());
    scrollPanel.setItemWidth(textureWidth);
    scrollPanel.setItemHeight(textureHeight);

    // selectedIconText = new Text(clippingEntity.getWidth() / 2, textureY, ResourcesManager.getInstance().fontIcon, FontAwesomeString.OK, vbom);

    // Iteration for menu item
    for (int i = 0; i < scrollPanel.getItemCount(); i++) {
      Sprite sprite = new Sprite(textureX + i * scrollPanel.getItemWidth(), textureY, textureRegions.get(i), vbom) {
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
          return false;
        }
      };
      scrollPanel.attachChild(sprite);
      pScene.registerTouchArea(sprite);
    }
    //clippingEntity.attachChild(selectedIconText);

    
    // Create select button
    selectButton = new ButtonSprite(0, 0, ResourcesManager.getInstance().selectButtonRegions[0], ResourcesManager.getInstance().selectButtonRegions[1], ResourcesManager.getInstance().selectButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        selectMenu(scrollPanel.getCurrentIndex());
        SceneManager.getInstance().getCurrentScene().playSound(BaseScene.CLICK_MUSIC);
      }
    });
    selectButton.setPosition(clippingEntity.getWidth()-42, selectButton.getHeight()/2 + 5);
    attachChild(selectButton);
    pScene.registerTouchArea(selectButton);

    
    createNavButton(pScene, vbom);

    deselectMenu();
    // creating new thread for gesture listener
    createNewThread();
  }
  
  
  
  
  private void createNavButton(Scene pScene, VertexBufferObjectManager vbom) {
    int slope = 0;
    if(SceneManager.getInstance().getCurrentSceneType() == SceneType.SCENE_RAM_SELECTION){
      slope = 32;
    }
    
    // Create nav button
    navLeftButton = new ButtonSprite(0, 0, ResourcesManager.getInstance().navLeftButtonRegions[0], ResourcesManager.getInstance().navLeftButtonRegions[1], ResourcesManager.getInstance().navLeftButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(scrollPanel.getCurrentIndex() > 0) {
          if(autoselect){
            selectMenu(scrollPanel.getCurrentIndex() - 1);
          }
          else {
            scrollPanel.navigate(-1);
            deselectMenu();
          }
          SceneManager.getInstance().getCurrentScene();
          SceneManager.getInstance().getCurrentScene().playSound(BaseScene.SWIPE_MUSIC);
        }
      }
    });
    navLeftButton.setPosition(-22, this.getHeight()/2 - slope);
    attachChild(navLeftButton);
    pScene.registerTouchArea(navLeftButton);
    // Create nav button right
    navRightButton = new ButtonSprite(0, 0, ResourcesManager.getInstance().navRightButtonRegions[0], ResourcesManager.getInstance().navRightButtonRegions[1], ResourcesManager.getInstance().navRightButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(scrollPanel.getCurrentIndex() < getScrollPanel().getItemCount() - 1) {
          if(autoselect){
            selectMenu(scrollPanel.getCurrentIndex() + 1);
          }
          else {
            scrollPanel.navigate(1);
            deselectMenu();
          }
          SceneManager.getInstance().getCurrentScene();
          SceneManager.getInstance().getCurrentScene().playSound(BaseScene.SWIPE_MUSIC);
        }
      }
    });
    navRightButton.setPosition(this.getWidth() + 22, this.getHeight()/2 + slope);
    attachChild(navRightButton);
    pScene.registerTouchArea(navRightButton);
  }

  
  
  
  private void createNewThread() {

    ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

      public void run() {
        mDetector = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener() {
          public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            
            if(!autoselect){
            
              float newVelocityX = 0;
              float vValue = scrollPanel.getItemWidth() * 10;
              if (velocityX > vValue)
                newVelocityX = vValue;
              if (velocityX < -vValue)
                newVelocityX = -vValue;
              scrollPanel.getmPhysicsHandler().setVelocityX(newVelocityX / ratio);
  
              float velo = scrollPanel.getItemWidth() * 2;
              if (velocityX > 0) {
                // moving right means index decreasing
  
                scrollPanel.setMovingRight(true);
                // scrollPanel.getmPhysicsHandler().setAccelerationX(-vValue);
  
                scrollPanel.getmPhysicsHandler().setAccelerationX(0);
                scrollPanel.getmPhysicsHandler().setVelocityX(velo);
                scrollPanel.setCurrentIndex(scrollPanel.getCurrentIndex() - 1);
              } else {
                scrollPanel.setMovingRight(false);
                // scrollPanel.getmPhysicsHandler().setAccelerationX(vValue);
  
                scrollPanel.getmPhysicsHandler().setAccelerationX(0);
                scrollPanel.getmPhysicsHandler().setVelocityX(-velo);
                scrollPanel.setCurrentIndex(scrollPanel.getCurrentIndex() + 1);
              }

            }
            deselectMenu();
            SceneManager.getInstance().getCurrentScene();
            SceneManager.getInstance().getCurrentScene().playSound(BaseScene.SWIPE_MUSIC);
            return true;
          }

          public boolean onSingleTapUp(MotionEvent e) {
            if (scrollPanel.getCurrentIndex() != scrollPanel.getSelectedIndex()) {
              SceneManager.getInstance().getCurrentScene();
              SceneManager.getInstance().getCurrentScene().playSound(BaseScene.CLICK_MUSIC);
              selectMenu(scrollPanel.getCurrentIndex());
            } else {
              deselectMenu();
            }
            return false;
          }
        });
      }
    });

  }

  public interface DeselectListener {
    void onSelect();
    void onDeselect();
  }
  
  public void updateNavigationButtonState(){
    navLeftButton.setEnabled( scrollPanel.getCurrentIndex() <= 0 ? false : true );
    navRightButton.setEnabled( scrollPanel.getCurrentIndex() >= getScrollPanel().getItemCount() - 1 ? false : true );
    if(autoselect){
      scrollPanel.setSelectedIndex(scrollPanel.getCurrentIndex());
      deselectListener.onSelect();
    }
  }
}
