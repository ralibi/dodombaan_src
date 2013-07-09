package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MultiplayerMenuScene extends BaseScene {

  ButtonSprite backButton;
  
  ButtonSprite singleDeviceButton;
  ButtonSprite bluetoothButton;
  ButtonSprite createChampionshipButton;
  
  @Override
  public void createScene() {
    createBackground();
    createForeground();
    createToolbar();
    createMenu();
  }

  private void createForeground() {
    Sprite landMainCharacter = new Sprite(205,  125, resourcesManager.landMainCharacter, vbom);
    Sprite mainCharacter = new Sprite(250,  200, resourcesManager.mainCharacter, vbom);
    Sprite secondaryCharacter = new Sprite(100,  230, resourcesManager.secondaryCharacter, vbom);
    
    mainCharacter.setFlippedHorizontal(true);
    mainCharacter.setScale(0.8f);
    
    attachChild(landMainCharacter);
    attachChild(mainCharacter);
    attachChild(secondaryCharacter);
  }

	private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.baseBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });

    Sprite bannerBottom = new Sprite(400, 32, resourcesManager.bannerBottomBackgroundRegion, vbom);
    attachChild(bannerBottom);
  }

  private void createToolbar() {
    Sprite bannerBottom = new Sprite(400, 32, resourcesManager.bannerBottomBackgroundRegion, vbom);
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
  }

  private void createMenu() {
    singleDeviceButton = new ButtonSprite(770, 400, resourcesManager.singleDeviceButtonRegions[0], resourcesManager.singleDeviceButtonRegions[1], resourcesManager.singleDeviceButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_MENU, SceneType.SCENE_MULTIPLAYER_SINGLE_DEVICE_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(singleDeviceButton);
    attachChild(singleDeviceButton);
    singleDeviceButton.setX(singleDeviceButton.getX() - singleDeviceButton.getWidth()/2);
    

    bluetoothButton = new ButtonSprite(770, 400 - 60, resourcesManager.bluetoothButtonRegions[0], resourcesManager.bluetoothButtonRegions[1], resourcesManager.bluetoothButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_MENU, SceneType.SCENE_MULTIPLAYER_BLUETOOTH_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(bluetoothButton);
    attachChild(bluetoothButton);
    bluetoothButton.setX(bluetoothButton.getX() - bluetoothButton.getWidth()/2);
    

    createChampionshipButton = new ButtonSprite(770, 400 - 120, resourcesManager.createChampionshipButtonRegions[0], resourcesManager.createChampionshipButtonRegions[1], resourcesManager.createChampionshipButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_MENU, SceneType.SCENE_MULTIPLAYER_CHAMPIONSHIP_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(createChampionshipButton);
    attachChild(createChampionshipButton);
    createChampionshipButton.setX(createChampionshipButton.getX() - createChampionshipButton.getWidth()/2);
  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_MENU, SceneType.SCENE_MAIN_MENU);
  }

  @Override
  public void unTouchScrollMenu() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public SceneType getSceneType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void disposeScene() {
    // TODO Auto-generated method stub
    
  }

}
