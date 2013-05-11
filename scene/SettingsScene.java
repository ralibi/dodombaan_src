package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.CheckboxEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.CheckboxEntity.ToggleListener;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class SettingsScene extends BaseScene {

  ButtonSprite doneButton;
  
  ScrollMenuEntity scrollTotalRound;
  CheckboxEntity sfxCheckbox;
  CheckboxEntity musicCheckbox;
  CheckboxEntity vibrationCheckbox;
  
  @Override
  public void createScene() {
    createBackground();
    attachChild(new Sprite(400, 240, resourcesManager.largePopupBackgroundRegion, vbom));
    
    createSettingsList();
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

  private void createSettingsList() {
    scrollTotalRound = new ScrollMenuEntity(200, 265, 40, 38, new Sprite(0, 0, resourcesManager.comboboxRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        gameDataManager.totalRoundSetting = (scrollTotalRound.getSelectedMenuIndex() * 2) + 3;
      }

      @Override
      public void onDeselect() {
      }
    });
    scrollTotalRound.buildSprite(19, 19, 38, 38, resourcesManager.totalScrollRegions, this, vbom);
    registerTouchArea(scrollTotalRound);
    registerTouchArea(scrollTotalRound.getScrollPanel().getChildByIndex(0));
    attachChild(scrollTotalRound);
    scrollTotalRound.setAutoselect(true);
    
    
    sfxCheckbox = new CheckboxEntity(300, 300, this, vbom, "sfx", gameDataManager.sfxSetting, new ToggleListener() {
      @Override
      public void onchange() {
        updateSftSetting();
      }
    });
    attachChild(sfxCheckbox);
    
    musicCheckbox = new CheckboxEntity(300, 240, this, vbom, "music", gameDataManager.musicSetting, new ToggleListener() {
      @Override
      public void onchange() {
        updateMusicSetting();
      }
    });
    attachChild(musicCheckbox);
    
    vibrationCheckbox = new CheckboxEntity(300, 180, this, vbom, "vibrate", gameDataManager.vibrationSetting, new ToggleListener() {
      @Override
      public void onchange() {
        updateVibrationSetting();
      }
    });
    attachChild(vibrationCheckbox);
  }

  protected void updateSftSetting() {
    if(sfxCheckbox != null){
      gameDataManager.sfxSetting = sfxCheckbox.isChecked();
    }
  }
  protected void updateMusicSetting() {
    if(musicCheckbox != null){
      gameDataManager.musicSetting = musicCheckbox.isChecked();
    }
  }
  protected void updateVibrationSetting() {
    if(vibrationCheckbox != null){
      gameDataManager.vibrationSetting = vibrationCheckbox.isChecked();
    }
  }

  private void createMenuChildScene() {
    doneButton = new ButtonSprite(400, 100, resourcesManager.doneNormalRegion, resourcesManager.donePressedRegion, resourcesManager.doneDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadMenuSceneFromSettings(engine);
      }
    });
    registerTouchArea(doneButton);
    attachChild(doneButton);
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
