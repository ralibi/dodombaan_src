package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.CheckboxEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.CheckboxEntity.ToggleListener;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.ResourcesManager;
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
    //attachChild(new Sprite(400, 240, resourcesManager.largePopupBackgroundRegion, vbom));
    
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
    attachChild(new Text(350, 360, ResourcesManager.getInstance().fontSmall, "total round", vbom));
    scrollTotalRound = new ScrollMenuEntity(450, 360, 40, 38, new Sprite(0, 0, resourcesManager.comboboxRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        gameDataManager.setTotalRoundSetting((scrollTotalRound.getSelectedMenuIndex() * 2) + 3);
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
    scrollTotalRound.selectMenu((gameDataManager.getTotalRoundSetting() - 3) / 2);

    sfxCheckbox = new CheckboxEntity(350, 300, this, vbom, "sfx", gameDataManager.isSfxSetting(), new ToggleListener() {
      @Override
      public void onchange() {
        if(sfxCheckbox != null){
          gameDataManager.setSfxSetting(sfxCheckbox.isChecked());
          playSound(CLICK_MUSIC);
        }
      }
    });
    attachChild(sfxCheckbox);
    
    musicCheckbox = new CheckboxEntity(350, 240, this, vbom, "music", gameDataManager.isMusicSetting(), new ToggleListener() {
      @Override
      public void onchange() {
        if(musicCheckbox != null){
          gameDataManager.setMusicSetting(musicCheckbox.isChecked());
          playSound(CLICK_MUSIC);
        }
      }
    });
    attachChild(musicCheckbox);
    
    vibrationCheckbox = new CheckboxEntity(350, 180, this, vbom, "vibrate", gameDataManager.isVibrationSetting(), new ToggleListener() {
      @Override
      public void onchange() {
        if(vibrationCheckbox != null){
          gameDataManager.setVibrationSetting(vibrationCheckbox.isChecked());
          playSound(CLICK_MUSIC);
        }
      }
    });
    attachChild(vibrationCheckbox);
  }

  private void createMenuChildScene() {
    doneButton = new ButtonSprite(400, 100, resourcesManager.okButtonRegions[0], resourcesManager.okButtonRegions[1], resourcesManager.okButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        onBackKeyPressed();
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(doneButton);
    attachChild(doneButton);
  }

  @Override
  public void onBackKeyPressed() {
  	SceneManager.getInstance().changeScene(SceneType.SCENE_SETTINGS, SceneType.SCENE_MAIN_MENU);
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
