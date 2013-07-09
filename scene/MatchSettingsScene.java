package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MatchSettingsScene extends BaseScene {

  private ScrollMenuEntity scrollEntityArena;

  ButtonSprite nextButton;
  ButtonSprite backButton;

  @Override
  public void createScene() {
    createBackground();
    createMenuChildScene();
    createArenaScrollMenu();
  }

  private void createArenaScrollMenu() {
    scrollEntityArena = new ScrollMenuEntity(400, 268, 660, 380, new Sprite(0, 0, resourcesManager.arenaScrollBackgroundRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        nextButton.setEnabled(true);
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollEntityArena.buildSprite(320, 190, 640, 380, resourcesManager.matchSettingsArenaRegions, this, vbom);
    registerTouchArea(scrollEntityArena);
    attachChild(scrollEntityArena);
    scrollEntityArena.selectMenu(0);
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
    nextButton = new ButtonSprite(700, 35, resourcesManager.nextButtonRegions[0], resourcesManager.nextButtonRegions[1], resourcesManager.nextButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        gameDataManager.arenaIndex = scrollEntityArena.getSelectedMenuIndex();
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MATCH_SETTINGS, SceneType.SCENE_GAME_PLAY);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(nextButton);
    attachChild(nextButton);

    backButton = new ButtonSprite(100, 30, resourcesManager.backButtonRegions[0], resourcesManager.backButtonRegions[1], resourcesManager.backButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        onBackKeyPressed();
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(backButton);
    attachChild(backButton);

  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_MATCH_SETTINGS, SceneType.SCENE_RAM_SELECTION);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_MATCH_SETTINGS;
  }

  @Override
  public void disposeScene() {
    // TODO Auto-generated method stub

  }

  @Override
  public void unTouchScrollMenu() {
    this.scrollEntityArena.getScrollPanel().setTouching(false);
  }

}
