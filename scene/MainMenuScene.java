package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
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
  ButtonSprite start2PlayerButton;
  ButtonSprite settingsButton;

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

    Sprite grass = new Sprite(400,  0, resourcesManager.grassRegion, vbom);
    Sprite logoText = new Sprite(400,  360, resourcesManager.logoTextRegion, vbom);
    Sprite leftLeaf = new Sprite(80,  90, resourcesManager.leftLeafRegion, vbom);
    Sprite rightLeaf = new Sprite(800 - 90,  140, resourcesManager.rightLeafRegion, vbom);

    attachChild(grass);
    attachChild(leftLeaf);
    attachChild(rightLeaf);
    attachChild(logoText);
  }

  @Override
  public void onBackKeyPressed() {
    System.exit(0);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_MENU;
  }

  @Override
  public void disposeScene() {
    // TODO Auto-generated method stub

  }

  private void createMenuChildScene() {
    start2PlayerButton = new ButtonSprite(400, 240, resourcesManager.start2PlayerNormalRegion, resourcesManager.start2PlayerPressedRegion, resourcesManager.start2PlayerDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadSheepSelectionSceneFromMenu(engine);
      }
    });
    registerTouchArea(start2PlayerButton);
    attachChild(start2PlayerButton);
    
    settingsButton = new ButtonSprite(400, 240 - 80, resourcesManager.settingsNormalRegion, resourcesManager.settingsPressedRegion, resourcesManager.settingsDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadSettingsScene(engine);
      }
    });
    registerTouchArea(settingsButton);
    attachChild(settingsButton);
  }


  @Override
  public void unTouchScrollMenu() {
    // TODO Auto-generated method stub

  }

}
