package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class RamSelectionScene extends BaseScene {

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------
  private ScrollMenuEntity scrollMenuRamP1;
  private ScrollMenuEntity scrollMenuRamP2;

  ButtonSprite nextButton;
  ButtonSprite backButton;

  @Override
  public void createScene() {
    createBackground();

    createToolbar();
    
    createMenu();
    createScrollMenuRam();
  }

  private void createToolbar() {
    Sprite bannerBottom = new Sprite(400,  32, resourcesManager.bannerBottomBackgroundRegion, vbom);
    attachChild(bannerBottom);

    backButton = new ButtonSprite(50, 32, resourcesManager.backButtonRegions[0], resourcesManager.backButtonRegions[1], resourcesManager.backButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
          onBackKeyPressed();
          playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(backButton);
    bannerBottom.attachChild(backButton);

    nextButton = new ButtonSprite(800 - 16, 32, resourcesManager.nextButtonRegions[0], resourcesManager.nextButtonRegions[1], resourcesManager.nextButtonRegions[2], vbom, new OnClickListener() {
        @Override
        public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
          gameDataManager.p1RamIndex = scrollMenuRamP1.getSelectedMenuIndex();
          gameDataManager.p2RamIndex = scrollMenuRamP2.getSelectedMenuIndex();
        	SceneManager.getInstance().changeScene(SceneType.SCENE_RAM_SELECTION, SceneType.SCENE_MATCH_SETTINGS);
          
          playSound(CLICK_MUSIC);
        }
    });
    nextButton.setX(nextButton.getX() - nextButton.getWidth()/2);
    registerTouchArea(nextButton);
    bannerBottom.attachChild(nextButton);
  }

  private void createScrollMenuRam() {
    scrollMenuRamP1 = new ScrollMenuEntity(200, 268, 310, 390, new Sprite(0, 0, resourcesManager.ramScrollBackgroundRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        if (scrollMenuRamP2.getSelectedMenuIndex() >= 0) {
          nextButton.setEnabled(true);
        }
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollMenuRamP1.buildSprite(155, 230, 310, 390, resourcesManager.ramSelectionRamRegions, this, vbom);
    registerTouchArea(scrollMenuRamP1);
    registerTouchArea(scrollMenuRamP1.getScrollPanel().getChildByIndex(0));
    attachChild(scrollMenuRamP1);
    // attachChild(scrollMenuRamP1.getSelectButton());

    buildRamCharacteristicMenu(scrollMenuRamP1);

    scrollMenuRamP2 = new ScrollMenuEntity(600, 268, 310, 390, new Sprite(0, 0, resourcesManager.ramScrollBackgroundRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        if (scrollMenuRamP1.getSelectedMenuIndex() >= 0) {
          nextButton.setEnabled(true);
        }
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollMenuRamP2.buildSprite(155, 230, 310, 390, resourcesManager.ramSelectionRamRegions, this, vbom);
    registerTouchArea(scrollMenuRamP2);
    registerTouchArea(scrollMenuRamP2.getScrollPanel().getChildByIndex(0));
    attachChild(scrollMenuRamP2);

    buildRamCharacteristicMenu(scrollMenuRamP2);

    scrollMenuRamP1.selectMenu(1);
    scrollMenuRamP2.selectMenu(2);
  }

  private void buildRamCharacteristicMenu(ScrollMenuEntity scrollMenuRam) {
    // TODO Auto-generated method stub

    for (int i = 0; i < scrollMenuRam.getScrollPanel().getItemCount(); i++) {

      final Entity characteristicGroup = new Entity(0, 0);
      int anchorX = i * 310 + 15;
      int anchorY = 100;

      characteristicGroup.attachChild(new Sprite(anchorX - 15 + 310/2, 52, resourcesManager.ramScrollToolbarRegion, vbom));
      characteristicGroup.attachChild(new Text(anchorX - 15 + 200, 85, resourcesManager.fontScore, GameConfigurationManager.RAM_NAME[i], vbom));
      
      // Strength
      final Entity strengthGroup = new Entity(0, 0);
      Text strenghtText = new Text(0, 0, resourcesManager.fontSmall, "Strength", vbom);
      strenghtText.setPosition(anchorX + strenghtText.getWidth() / 2f, anchorY - 10);
      strengthGroup.attachChild(strenghtText);
      strengthGroup.attachChild(getCharacteristicMeter(anchorX, anchorY - 25, GameConfigurationManager.STRENGTH[i]));
      characteristicGroup.attachChild(strengthGroup);

      anchorY -= 31;

      // Speed
      final Entity speedGroup = new Entity(0, 0);
      Text speedText = new Text(0, 0, resourcesManager.fontSmall, "Speed", vbom);
      speedText.setPosition(anchorX + speedText.getWidth() / 2f, anchorY - 10);
      speedGroup.attachChild(speedText);
      speedGroup.attachChild(getCharacteristicMeter(anchorX, anchorY - 25, GameConfigurationManager.SPEED[i]));
      characteristicGroup.attachChild(speedGroup);

      anchorY -= 31;

      final Entity agilityGroup = new Entity(0, 0);
      Text agilityText = new Text(0, 0, resourcesManager.fontSmall, "Agility", vbom);
      agilityText.setPosition(anchorX + agilityText.getWidth() / 2f, anchorY - 10);
      agilityGroup.attachChild(agilityText);
      agilityGroup.attachChild(getCharacteristicMeter(anchorX, anchorY - 25, GameConfigurationManager.AGILITY[i]));
      characteristicGroup.attachChild(agilityGroup);
      
      
      scrollMenuRam.getScrollPanel().attachChild(characteristicGroup);
    }
  }

  private Entity getCharacteristicMeter(int posX, int posY, int nRect) {

	posX = posX + 11;
	
    /* Create the rectangles. */
    final Entity strengthRectangleGroup = new Entity(0, 0);
    for (int i = 0; i < nRect; i++) {
      strengthRectangleGroup.attachChild(new Sprite(posX + i * 23, posY, resourcesManager.powerFilledRegion, vbom));
    }
    for (int i = nRect; i < 4; i++) {
      strengthRectangleGroup.attachChild(new Sprite(posX + i * 23, posY, resourcesManager.powerBlankRegion, vbom));
    }
    return strengthRectangleGroup;
  }

  protected void itemClick(int i) {
    Debug.d("Clicking: " + i);
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.baseBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });
  }

  private void createMenu() {


  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_RAM_SELECTION, SceneManager.getInstance().prevSceneType);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_RAM_SELECTION;
  }

  @Override
  public void disposeScene() {
    // scrollMenuRamP1.detachChildren();
    // scrollMenuRamP2.detachChildren();
    // detachChildren();
  }

  @Override
  public void unTouchScrollMenu() {
    this.scrollMenuRamP1.getScrollPanel().setTouching(false);
    this.scrollMenuRamP2.getScrollPanel().setTouching(false);
  }

}
