package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class ClientMenuScene extends BaseScene {

  ButtonSprite backButton;
  
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
  }

	@Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_CLIENT_MENU, SceneType.SCENE_MULTIPLAYER_BLUETOOTH_MENU);
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
