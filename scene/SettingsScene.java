package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class SettingsScene extends BaseScene {

  ButtonSprite okButton;
  ButtonSprite resetButton;

  @Override
  public void createScene() {
    createBackground();
    createMenuChildScene();
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.settingsBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });
  }

  private void createMenuChildScene() {
    okButton = new ButtonSprite(400, 240 - 80, resourcesManager.settingsOkNormalRegion, resourcesManager.settingsOkPressedRegion, resourcesManager.settingsOkDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadMenuSceneFromSettings(engine);
      }
    });
    registerTouchArea(okButton);
    attachChild(okButton);
    
    resetButton = new ButtonSprite(400, 240, resourcesManager.settingsResetNormalRegion, resourcesManager.settingsResetPressedRegion, resourcesManager.settingsResetDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        // TODO Auto-generated method stub
      }
    });
    registerTouchArea(resetButton);
    attachChild(resetButton);
  }

  @Override
  public void onBackKeyPressed() {
    SceneManager.getInstance().loadMenuSceneFromSettings(engine);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_SETTINGS;
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
