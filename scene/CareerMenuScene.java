package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class CareerMenuScene extends BaseScene {

  ButtonSprite backButton;
  ButtonSprite championshipButton, championshipIcon;
  ButtonSprite exerciseButton, exerciseIcon;

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

    championshipIcon = new ButtonSprite(215, 300, resourcesManager.championshipIconRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_CAREER_MENU, SceneType.SCENE_CHAMPIONSHIP_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(championshipIcon);
    attachChild(championshipIcon);
    
    championshipButton = new ButtonSprite(200, 140, resourcesManager.championshipButtonRegions[0], resourcesManager.championshipButtonRegions[1], resourcesManager.championshipButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_CAREER_MENU, SceneType.SCENE_CHAMPIONSHIP_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(championshipButton);
    attachChild(championshipButton);
    
    exerciseIcon = new ButtonSprite(605, 300, resourcesManager.exerciseIconRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_CAREER_MENU, SceneType.SCENE_EXERCISE_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(exerciseIcon);
    attachChild(exerciseIcon);

    exerciseButton = new ButtonSprite(600, 140, resourcesManager.exerciseButtonRegions[0], resourcesManager.exerciseButtonRegions[1], resourcesManager.exerciseButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_CAREER_MENU, SceneType.SCENE_EXERCISE_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(exerciseButton);
    attachChild(exerciseButton);
  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_CAREER_MENU, SceneType.SCENE_MAIN_MENU);
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
