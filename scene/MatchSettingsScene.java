package com.ralibi.dodombaan.scene;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MatchSettingsScene extends BaseScene {

  private ScrollMenuEntity scrollEntityArena;

  ButtonSprite nextButton;
  ButtonSprite backButton;
  ButtonSprite dryButton;
  ButtonSprite wetButton;
  
	List<Sprite> mudSprites;
  
  ScrollMenuEntity scrollTotalRound;

  @Override
  public void createScene() {
    createBackground();
    createToolbar();
    createOtherSettings();
    createArenaScrollMenu();

    setDried(gameDataManager.isWeatherDriedSetting());
  }
  
  private void createToolbar() {
    Sprite bannerBottom = new Sprite(400,  32, resourcesManager.bannerBottomBackgroundRegion, vbom);
    attachChild(bannerBottom);

    nextButton = new ButtonSprite(800 - 16, 32, resourcesManager.nextButtonRegions[0], resourcesManager.nextButtonRegions[1], resourcesManager.nextButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        gameDataManager.arenaIndex = scrollEntityArena.getSelectedMenuIndex();
      	SceneManager.getInstance().changeScene(SceneType.SCENE_MATCH_SETTINGS, SceneType.SCENE_GAME_PLAY);
        playSound(CLICK_MUSIC);
      }
    });
    nextButton.setX(nextButton.getX() - nextButton.getWidth()/2);
    registerTouchArea(nextButton);
    bannerBottom.attachChild(nextButton);

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

  private void createOtherSettings() {
    attachChild(new Text(530, 340, ResourcesManager.getInstance().fontScore, "total round", vbom));
    scrollTotalRound = new ScrollMenuEntity(700, 340, 40, 38, new Sprite(0, 0, resourcesManager.comboboxRegion, vbom), this, new DeselectListener() {
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

    attachChild(new Text(530, 240, ResourcesManager.getInstance().fontScore, "weather", vbom));
    dryButton = new ButtonSprite(700, 240, resourcesManager.dryButtonRegions[0], resourcesManager.dryButtonRegions[1], resourcesManager.dryButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        setDried(true);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(dryButton);
    attachChild(dryButton);
    

    wetButton = new ButtonSprite(700, 180, resourcesManager.wetButtonRegions[0], resourcesManager.wetButtonRegions[1], resourcesManager.wetButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        setDried(false);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(wetButton);
    attachChild(wetButton);
    
  }

	private void createArenaScrollMenu() {
		Sprite panelBackground = new Sprite(225, 235, resourcesManager.arenaScrollBackgroundRegion, vbom);
		panelBackground.setScaleX(0.48f);
		panelBackground.setScaleY(0.8f);
		attachChild(panelBackground);
		
    scrollEntityArena = new ScrollMenuEntity(225, 235, 320, 320, null, this, new DeselectListener() {
      @Override
      public void onSelect() {
        nextButton.setEnabled(true);
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollEntityArena.buildSprite(160, 160, 320, 320, resourcesManager.matchSettingsArenaRegions, this, vbom);
    registerTouchArea(scrollEntityArena);
    attachChild(scrollEntityArena);
    scrollEntityArena.selectMenu(0);
    
    mudSprites = new ArrayList<Sprite>();
    mudSprites.clear();
    for (int i = 0; i < scrollEntityArena.getScrollPanel().getItemCount(); i++) {
      mudSprites.add(new Sprite(160 + i * 320, 160, resourcesManager.mudRegion, vbom));
      mudSprites.get(i).setScale(0.4f);
      scrollEntityArena.getScrollPanel().attachChild(mudSprites.get(i));
      
      scrollEntityArena.getScrollPanel().attachChild(new Text(160 + i * 320, 270, resourcesManager.fontScore, GameConfigurationManager.ARENA_NAME[i], vbom));
    }
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
  
  public void setDried(boolean dried){
    dryButton.setEnabled(!dried);
    wetButton.setEnabled(dried);
    gameDataManager.setWeatherDriedSetting(dried);
    for (int i = 0; i < mudSprites.size(); i++) {
	    mudSprites.get(i).setVisible(!dried);
    }
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
