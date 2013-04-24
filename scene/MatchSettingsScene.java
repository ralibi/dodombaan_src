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
    scrollEntityArena = new ScrollMenuEntity(400, 240, 720, 240, new DeselectListener() {
      @Override
      public void onSelect() {
        nextButton.setEnabled(true);
        resourcesManager.minorSound.play();
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollEntityArena.buildSprite(300, 120, 600, 240, resourcesManager.matchSettingsArenaRegions, this, vbom);
    registerTouchArea(scrollEntityArena);
    attachChild(scrollEntityArena);
    scrollEntityArena.selectMenu(0);
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.matchSettingsBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });
  }

  private void createMenuChildScene() {
    nextButton = new ButtonSprite(600, 40, resourcesManager.nextNormalRegion, resourcesManager.nextPressedRegion, resourcesManager.nextDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        gameDataManager.arenaIndex = scrollEntityArena.getSelectedMenuIndex();
        SceneManager.getInstance().loadGamePlayScene(engine);
      }
    });
    registerTouchArea(nextButton);
    attachChild(nextButton);
    
    backButton = new ButtonSprite(200, 40, resourcesManager.backNormalRegion, resourcesManager.backPressedRegion, resourcesManager.backDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadSheepSelectionSceneFromMatchSettings(engine);
      }
    });
    registerTouchArea(backButton);
    attachChild(backButton);
    
  }

  @Override
  public void onBackKeyPressed() {
    SceneManager.getInstance().loadSheepSelectionSceneFromMatchSettings(engine);
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
