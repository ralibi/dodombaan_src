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
  ButtonSprite changeSheepButton;
  // ButtonSprite changeArenaButton;
  ButtonSprite backToMenuButton;
  ButtonSprite exitButton;

  @Override
  public void createScene() {
    createBackground();
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
  }

  private void createMenuChildScene() {

    rematchButton = new ButtonSprite(400, 240, resourcesManager.matchOverRematchNormalRegion, resourcesManager.matchOverRematchPressedRegion, resourcesManager.matchOverRematchDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadGamePlaySceneFromMatchOver(engine);
      }
    });
    registerTouchArea(rematchButton);
    attachChild(rematchButton);

    changeSheepButton = new ButtonSprite(400, 240 - 80, resourcesManager.matchOverChangeSheepNormalRegion, resourcesManager.matchOverChangeSheepPressedRegion, resourcesManager.matchOverChangeSheepDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadSheepSelectionSceneFromMatchOver(engine);
      }
    });
    registerTouchArea(changeSheepButton);
    attachChild(changeSheepButton);

    backToMenuButton = new ButtonSprite(400, 240 - 160, resourcesManager.matchOverBackToMenuNormalRegion, resourcesManager.matchOverBackToMenuPressedRegion, resourcesManager.matchOverBackToMenuDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadMenuSceneFromMatchOver(engine);
      }
    });
    registerTouchArea(backToMenuButton);
    attachChild(backToMenuButton);

    exitButton = new ButtonSprite(400, 240, resourcesManager.matchOverExitNormalRegion, resourcesManager.matchOverExitPressedRegion, resourcesManager.matchOverExitDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

      }
    });
    // registerTouchArea(exitButton);
    // attachChild(exitButton);
  }

  @Override
  public void onBackKeyPressed() {
    SceneManager.getInstance().loadMenuSceneFromMatchOver(engine);
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
