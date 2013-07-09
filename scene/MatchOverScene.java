package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MatchOverScene extends BaseScene {

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------
  ButtonSprite rematchButton;
  ButtonSprite changeRamButton;
  // ButtonSprite changeArenaButton;
  ButtonSprite mainMenuButton;
  ButtonSprite exitButton;

  @Override
  public void createScene() {
    createBackground();
    createPosingRam();
    createMenuChildScene();
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.baseBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });

    Sprite crowd = new Sprite(400, 240, resourcesManager.matchOverBackgroundRegion, vbom);
	if(gameDataManager.winner == 1){
		crowd.setFlippedHorizontal(true);
	}
    attachChild(crowd);
    
  }

  private void createPosingRam() {
	  
//	  rematchButton
//	  changeRamButton
//	  backToMenuButton
	  
	if(gameDataManager.winner == 1){
		Sprite winnerSprite = new Sprite(800-600, 180, resourcesManager.ramSelectionRamRegions.get(gameDataManager.p1RamIndex), vbom);
		//winnerSprite.setScale(0.4f);
		attachChild(winnerSprite);

		Sprite loserSprite = new Sprite(800-90, 130, resourcesManager.ramSelectionRamRegions.get(gameDataManager.p2RamIndex), vbom);
		loserSprite.setScale(0.4f);
		attachChild(loserSprite);
	}
	else{
		Sprite winnerSprite = new Sprite(600, 180, resourcesManager.ramSelectionRamRegions.get(gameDataManager.p2RamIndex), vbom);
		//winnerSprite.setScale(0.4f);
		attachChild(winnerSprite);

		Sprite loserSprite = new Sprite(90, 130, resourcesManager.ramSelectionRamRegions.get(gameDataManager.p1RamIndex), vbom);
		loserSprite.setScale(0.4f);
		attachChild(loserSprite);
	}
  }

  private void createMenuChildScene() {

    rematchButton = new ButtonSprite(130, 380, resourcesManager.rematchButtonRegions[0], resourcesManager.rematchButtonRegions[1], resourcesManager.rematchButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MATCH_OVER, SceneType.SCENE_GAME_PLAY);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(rematchButton);
    attachChild(rematchButton);

    changeRamButton = new ButtonSprite(135, 300, resourcesManager.changeRamButtonRegions[0], resourcesManager.changeRamButtonRegions[1], resourcesManager.changeRamButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MATCH_OVER, SceneType.SCENE_RAM_SELECTION);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(changeRamButton);
    attachChild(changeRamButton);

    mainMenuButton = new ButtonSprite(135, 242, resourcesManager.mainMenuButtonRegions[0], resourcesManager.mainMenuButtonRegions[1], resourcesManager.mainMenuButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        onBackKeyPressed();
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(mainMenuButton);
    attachChild(mainMenuButton);

    exitButton = new ButtonSprite(400, 240, resourcesManager.quitButtonRegions[0], resourcesManager.quitButtonRegions[1], resourcesManager.quitButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        playSound(CLICK_MUSIC);
      }
    });
    // registerTouchArea(exitButton);
    // attachChild(exitButton);
    

	if(gameDataManager.winner == 1){
		rematchButton.setX(800-rematchButton.getX());
		changeRamButton.setX(800-changeRamButton.getX());
		mainMenuButton.setX(800-mainMenuButton.getX());
	}
  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_MATCH_OVER, SceneType.SCENE_MAIN_MENU);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_MATCH_OVER;
  }

  @Override
  public void disposeScene() {
    // TODO Auto-generated method stub

  }

  @Override
  public void unTouchScrollMenu() {
    // TODO Auto-generated method stub

  }

}
