package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene {

  // ---------------------------------------------
  // CONSTANT
  // ---------------------------------------------


  

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------
	ButtonSprite singleDeviceBattleButton;
	ButtonSprite multiplayerButton;
  ButtonSprite careerButton;
  ButtonSprite myRamsButton;
  ButtonSprite myFriendsButton;

  ButtonSprite settingsButton;
  ButtonSprite helpButton;
  ButtonSprite wikiButton;
  ButtonSprite backButton;
  
  Scene wikiScene;
  Scene helpScene;

  @Override
  public void createScene() {
    createBackground();
    createToolbar();
    createMenu();

    createWikiChildScene();
    createHelpChildScene();
  }

  private void createWikiChildScene() {	
	wikiScene = new Scene();
	Sprite overlaySprite = new Sprite(400,  240, resourcesManager.overlayBlackRegion, vbom);
	wikiScene.attachChild(overlaySprite);
	
	Sprite fullPanelSprite = new Sprite(400,  240 + 32, resourcesManager.fullPanelRegion, vbom);
	wikiScene.attachChild(fullPanelSprite);

	Sprite wikiContent = new Sprite(400,  240 + 32, resourcesManager.wikiContentRegion, vbom);
	wikiScene.attachChild(wikiContent);
	
	wikiScene.setBackgroundEnabled(false);
	
	Sprite hideChildScene = new ButtonSprite(400, 100, resourcesManager.backButtonRegions[0], resourcesManager.backButtonRegions[1], resourcesManager.backButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	  wikiScene.back();
    	  setEnabledParentButton(true);
      }
    });
    registerTouchArea(hideChildScene);
    wikiScene.attachChild(hideChildScene);
  }
  

  private void createHelpChildScene() {	
	helpScene = new Scene();
	Sprite overlaySprite = new Sprite(400,  240, resourcesManager.overlayBlackRegion, vbom);
	helpScene.attachChild(overlaySprite);
	
	Sprite fullPanelSprite = new Sprite(400,  240 + 32, resourcesManager.fullPanelRegion, vbom);
	helpScene.attachChild(fullPanelSprite);

	Sprite helpContent = new Sprite(400,  240 + 32, resourcesManager.helpContentRegion, vbom);
	helpScene.attachChild(helpContent);
	
	helpScene.setBackgroundEnabled(false);
	
	Sprite hideChildScene = new ButtonSprite(400, 100, resourcesManager.backButtonRegions[0], resourcesManager.backButtonRegions[1], resourcesManager.backButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	  helpScene.back();
    	  setEnabledParentButton(true);
      }
    });
    registerTouchArea(hideChildScene);
    helpScene.attachChild(hideChildScene);
  }

private void setEnabledParentButton(boolean b) {
  multiplayerButton.setEnabled(b);
  careerButton.setEnabled(b);
  myRamsButton.setEnabled(b);
  myFriendsButton.setEnabled(b);

  settingsButton.setEnabled(b);
  helpButton.setEnabled(b);
  wikiButton.setEnabled(b);
  backButton.setEnabled(b);
}

private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.baseBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });

    Sprite landMainCharacter = new Sprite(205,  125, resourcesManager.landMainCharacter, vbom);
    Sprite mainCharacter = new Sprite(200,  230, resourcesManager.mainCharacter, vbom);
    mainCharacter.setFlippedHorizontal(true);
    
    Sprite bannerBottom = new Sprite(400,  32, resourcesManager.bannerBottomBackgroundRegion, vbom);

    attachChild(landMainCharacter);
    attachChild(mainCharacter);
    
    attachChild(bannerBottom);
  }

  private void createToolbar() {
    Sprite bannerBottom = new Sprite(400,  32, resourcesManager.bannerBottomBackgroundRegion, vbom);
    attachChild(bannerBottom);

    int baseToolbarButtonX = 630;
    
    settingsButton = new ButtonSprite(baseToolbarButtonX, 32, resourcesManager.settingsButtonRegions[0], resourcesManager.settingsButtonRegions[1], resourcesManager.settingsButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MAIN_MENU, SceneType.SCENE_SETTINGS);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(settingsButton);
    bannerBottom.attachChild(settingsButton);

    helpButton = new ButtonSprite(baseToolbarButtonX + 60, 32, resourcesManager.helpButtonRegions[0], resourcesManager.helpButtonRegions[1], resourcesManager.helpButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	  setChildScene(helpScene);
    	  setEnabledParentButton(false);
      }
    });
    registerTouchArea(helpButton);
    bannerBottom.attachChild(helpButton);

    wikiButton = new ButtonSprite(baseToolbarButtonX + 120, 32, resourcesManager.wikiButtonRegions[0], resourcesManager.wikiButtonRegions[1], resourcesManager.wikiButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	  setChildScene(wikiScene);
    	  setEnabledParentButton(false);
      }
    });
    registerTouchArea(wikiButton);
    bannerBottom.attachChild(wikiButton);


    backButton = new ButtonSprite(50, 32, resourcesManager.backButtonRegions[0], resourcesManager.backButtonRegions[1], resourcesManager.backButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	System.exit(0);
      }
    });
    registerTouchArea(backButton);
    bannerBottom.attachChild(backButton);
  }

  @Override
  public void onBackKeyPressed() {
    System.exit(0);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_MAIN_MENU;
  }

  @Override
  public void disposeScene() {
    // TODO Auto-generated method stub

  }

  private void createMenu() {
    singleDeviceBattleButton = new ButtonSprite(770, 400, resourcesManager.singleDeviceBattleButtonRegions[0], resourcesManager.singleDeviceBattleButtonRegions[1], resourcesManager.singleDeviceBattleButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MAIN_MENU, SceneType.SCENE_RAM_SELECTION);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(singleDeviceBattleButton);
    attachChild(singleDeviceBattleButton);
    singleDeviceBattleButton.setX(singleDeviceBattleButton.getX() - singleDeviceBattleButton.getWidth()/2);
    
    multiplayerButton = new ButtonSprite(770, 400 - 60, resourcesManager.multiplayerButtonRegions[0], resourcesManager.multiplayerButtonRegions[1], resourcesManager.multiplayerButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MAIN_MENU, SceneType.SCENE_MULTIPLAYER_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(multiplayerButton);
    attachChild(multiplayerButton);
    multiplayerButton.setX(multiplayerButton.getX() - multiplayerButton.getWidth()/2);
    
    careerButton = new ButtonSprite(770, 400 - 120, resourcesManager.careerButtonRegions[0], resourcesManager.careerButtonRegions[1], resourcesManager.careerButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MAIN_MENU, SceneType.SCENE_CAREER_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(careerButton);
    attachChild(careerButton);
    careerButton.setX(careerButton.getX() - careerButton.getWidth()/2);

    myRamsButton = new ButtonSprite(770, 400 - 180, resourcesManager.myRamsButtonRegions[0], resourcesManager.myRamsButtonRegions[1], resourcesManager.myRamsButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
      }
    });
    registerTouchArea(myRamsButton);
    attachChild(myRamsButton);
    myRamsButton.setX(myRamsButton.getX() - myRamsButton.getWidth()/2);
    

    myFriendsButton = new ButtonSprite(770, 400 - 240, resourcesManager.myFriendsButtonRegions[0], resourcesManager.myFriendsButtonRegions[1], resourcesManager.myFriendsButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
      }
    });
    registerTouchArea(myFriendsButton);
    attachChild(myFriendsButton);
    myFriendsButton.setX(myFriendsButton.getX() - myFriendsButton.getWidth()/2);
    
  }


  @Override
  public void unTouchScrollMenu() {
    // TODO Auto-generated method stub

  }

}
