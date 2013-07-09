package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MultiplayerBluetoothMenuScene extends BaseScene {

  ButtonSprite backButton;
  ButtonSprite hostButton, clientButton;
  
  @Override
  public void createScene() {
    createBackground();
    createToolbar();
    createMenu();
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
    hostButton = new ButtonSprite(400, 400 - 100, resourcesManager.hostButtonRegions[0], resourcesManager.hostButtonRegions[1], resourcesManager.hostButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_BLUETOOTH_MENU, SceneType.SCENE_HOST_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(hostButton);
    attachChild(hostButton);
    
    clientButton = new ButtonSprite(400, 400 - 180, resourcesManager.clientButtonRegions[0], resourcesManager.clientButtonRegions[1], resourcesManager.clientButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_BLUETOOTH_MENU, SceneType.SCENE_CLIENT_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(clientButton);
    attachChild(clientButton);
  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_MULTIPLAYER_BLUETOOTH_MENU, SceneType.SCENE_MULTIPLAYER_MENU);
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
