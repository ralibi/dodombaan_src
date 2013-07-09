package com.ralibi.dodombaan.base;

import java.io.IOException;

import org.andengine.audio.music.exception.MusicReleasedException;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.ralibi.dodombaan.MainActivity;
import com.ralibi.dodombaan.manager.GameDataManager;
import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public abstract class BaseScene extends Scene {
  public final static int CLICK_MUSIC = 1;
  public final static int SWIPE_MUSIC = 2;
  public final static int SHORT_WHISTLE_MUSIC = 3;
  public final static int LONG_WHISTLE_MUSIC = 4;
  public final static int CLAPPING_HAND_MUSIC = 5;

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------

  protected Engine engine;
  protected MainActivity activity;
  protected ResourcesManager resourcesManager;
  protected GameDataManager gameDataManager;
  protected VertexBufferObjectManager vbom;
  protected Camera camera;

  protected float CAMERA_WIDTH;
  protected float CAMERA_HEIGHT;

  // ---------------------------------------------
  // CONSTRUCTOR
  // ---------------------------------------------

  public BaseScene() {
    this.resourcesManager = ResourcesManager.getInstance();
    this.engine = resourcesManager.engine;
    this.activity = resourcesManager.activity;
    this.vbom = resourcesManager.vbom;
    this.camera = resourcesManager.camera;
    this.gameDataManager = GameDataManager.getInstance();
    this.setOnSceneTouchListener(ResourcesManager.getInstance().activity);

    this.CAMERA_WIDTH = this.camera.getWidth();
    this.CAMERA_HEIGHT = this.camera.getHeight();

    createScene();
  }

  // ---------------------------------------------
  // ABSTRACTION
  // ---------------------------------------------

  public abstract void createScene();

  public abstract void onBackKeyPressed();

  public abstract void unTouchScrollMenu();

  public abstract SceneType getSceneType();

  public abstract void disposeScene();

  public void playSound(int soundType) {
    if (gameDataManager.isSfxSetting()) {
      switch (soundType) {
        case CLICK_MUSIC:
          resourcesManager.clickMusic.play();
          break;
        case SWIPE_MUSIC:
          resourcesManager.swipeMusic.play();
          break;
        case SHORT_WHISTLE_MUSIC:
          resourcesManager.shortWhistleMusic.play();
          break;
        case LONG_WHISTLE_MUSIC:
          resourcesManager.longWhistleMusic.play();
          break;
        case CLAPPING_HAND_MUSIC:
          resourcesManager.clappingHandMusic.play();
          break;
        default:
          break;
      }
    }
  }

  public void playMusic() {
    if (gameDataManager.isSfxSetting()) {
      resourcesManager.bgmMusic.play();
    }
  }

  public void pauseMusic() {
    if (gameDataManager.isSfxSetting()) {
      resourcesManager.bgmMusic.pause();
    }
  }
  
  public void stopMusic() {
    if (gameDataManager.isSfxSetting() && resourcesManager.bgmMusic.isPlaying()) {
      resourcesManager.bgmMusic.stop();
      try {
        resourcesManager.bgmMusic.getMediaPlayer().prepare();
      } catch (MusicReleasedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      resourcesManager.bgmMusic.seekTo(0);
    }
  }
  
  public void vibrate(int strength) {
    if (gameDataManager.isVibrationSetting()) {
      resourcesManager.vibrator.vibrate(strength);
    }
  }
}
