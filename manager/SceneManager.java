package com.ralibi.dodombaan.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.scene.GamePlayScene;
import com.ralibi.dodombaan.scene.LoadingScene;
import com.ralibi.dodombaan.scene.MainMenuScene;
import com.ralibi.dodombaan.scene.MatchOverScene;
import com.ralibi.dodombaan.scene.MatchSettingsScene;
import com.ralibi.dodombaan.scene.SettingsScene;
import com.ralibi.dodombaan.scene.SheepSelectionScene;
import com.ralibi.dodombaan.scene.SplashScene;

public class SceneManager {
  // ---------------------------------------------
  // SCENES
  // ---------------------------------------------

  private BaseScene splashScene;
  private BaseScene menuScene;
  private BaseScene sheepSelectionScene;
  private BaseScene matchSettingsScene;
  private BaseScene settingsScene;
  private BaseScene matchOverScene;
  private BaseScene gamePlayScene;
  private BaseScene loadingScene;

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------

  private static final SceneManager INSTANCE = new SceneManager();

  private SceneType currentSceneType = SceneType.SCENE_SPLASH;

  private BaseScene currentScene;

  private Engine engine = ResourcesManager.getInstance().engine;

  public enum SceneType {
    SCENE_SPLASH, SCENE_MENU, SCENE_GAME_PLAY, SCENE_LOADING, SCENE_SETTINGS, SCENE_SHEEP_SELECTION, SCENE_MATCH_SETTINGS, SCENE_MATCH_OVER,
  }

  // ---------------------------------------------
  // CLASS LOGIC
  // ---------------------------------------------

  public void setScene(BaseScene scene) {
    engine.setScene(scene);
    currentScene = scene;
    currentSceneType = scene.getSceneType();
  }

  public void setScene(SceneType sceneType) {
    switch (sceneType) {
    case SCENE_MENU:
      setScene(menuScene);
      break;
    case SCENE_GAME_PLAY:
      setScene(gamePlayScene);
      break;
    case SCENE_SPLASH:
      setScene(splashScene);
      break;
    case SCENE_LOADING:
      setScene(loadingScene);
      break;
    case SCENE_SETTINGS:
      setScene(settingsScene);
      break;
    case SCENE_MATCH_SETTINGS:
      setScene(matchSettingsScene);
      break;
    case SCENE_SHEEP_SELECTION:
      setScene(sheepSelectionScene);
      break;
    case SCENE_MATCH_OVER:
      setScene(matchOverScene);
      break;
    default:
      break;
    }
  }

  public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
    ResourcesManager.getInstance().loadSplashScreen();
    splashScene = new SplashScene();
    currentScene = splashScene;
    pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
  }

  private void disposeSplashScene() {
    ResourcesManager.getInstance().unloadSplashScreen();
    splashScene.disposeScene();
    splashScene = null;
  }

  public void createMenuScene() {
    ResourcesManager.getInstance().loadMenuResources();
    menuScene = new MainMenuScene();
    loadingScene = new LoadingScene();
    setScene(menuScene);
    disposeSplashScene();
  }

  public void loadMatchSettingsSceneFromSheepSelection(final Engine mEngine) {
    setScene(loadingScene);
    sheepSelectionScene.disposeScene();
    ResourcesManager.getInstance().unloadSheepSelectionTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMatchSettingsResources();
        matchSettingsScene = new MatchSettingsScene();
        setScene(matchSettingsScene);
      }
    }));
  }

  public void loadSheepSelectionSceneFromMenu(final Engine mEngine) {
    setScene(loadingScene);
    menuScene.disposeScene();
    ResourcesManager.getInstance().unloadMenuTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadSheepSelectionResources();
        sheepSelectionScene = new SheepSelectionScene();
        setScene(sheepSelectionScene);
      }
    }));
  }

  public void loadSheepSelectionSceneFromMatchSettings(final Engine mEngine) {
    setScene(loadingScene);
    matchSettingsScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchSettingsTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadSheepSelectionResources();
        sheepSelectionScene = new SheepSelectionScene();
        setScene(sheepSelectionScene);
      }
    }));
  }

  public void loadSheepSelectionSceneFromMatchOver(final Engine mEngine) {
    setScene(loadingScene);
    matchOverScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchOverTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadSheepSelectionResources();
        sheepSelectionScene = new SheepSelectionScene();
        setScene(sheepSelectionScene);
      }
    }));
  }

  public void loadMenuSceneFromSheepSelection(final Engine mEngine) {
    setScene(loadingScene);
    sheepSelectionScene.disposeScene();
    ResourcesManager.getInstance().unloadSheepSelectionTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMenuTextures();
        setScene(menuScene);
      }
    }));
  }

  public void loadMenuSceneFromGamePlay(final Engine mEngine) {
    setScene(loadingScene);
    gamePlayScene.disposeScene();
    ResourcesManager.getInstance().unloadGamePlayTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMenuTextures();
        setScene(menuScene);
      }
    }));
  }

  public void loadMenuSceneFromSettings(final Engine mEngine) {
    setScene(loadingScene);
    settingsScene.disposeScene();
    ResourcesManager.getInstance().unloadSettingsTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMenuTextures();
        setScene(menuScene);
      }
    }));
  }

  public void loadMenuSceneFromMatchOver(final Engine mEngine) {
    setScene(loadingScene);
    matchOverScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchOverTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMenuTextures();
        setScene(menuScene);
      }
    }));
  }

  public void loadGamePlayScene(final Engine mEngine) {
    setScene(loadingScene);
    matchSettingsScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchSettingsTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadGamePlayResources();
        gamePlayScene = new GamePlayScene();
        setScene(gamePlayScene);
      }
    }));
  }

  public void loadGamePlaySceneFromMatchOver(final Engine mEngine) {
    setScene(loadingScene);
    matchOverScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchOverTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadGamePlayResources();
        gamePlayScene = new GamePlayScene();
        setScene(gamePlayScene);
      }
    }));
  }

  public void reloadGamePlay(final Engine mEngine) {
    setScene(loadingScene);
    gamePlayScene.disposeScene();
    ResourcesManager.getInstance().unloadGamePlayTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadGamePlayResources();
        gamePlayScene = new GamePlayScene();
        setScene(gamePlayScene);
      }
    }));
  }

  public void loadMatchOverScene(final Engine mEngine) {
    setScene(loadingScene);
    gamePlayScene.disposeScene();
    ResourcesManager.getInstance().unloadGamePlayTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMatchOverResources();
        matchOverScene = new MatchOverScene();
        setScene(matchOverScene);
      }
    }));
  }

  public void loadSettingsScene(final Engine mEngine) {
    setScene(loadingScene);
    menuScene.disposeScene();
    ResourcesManager.getInstance().unloadMenuTextures();
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadSettingsResources();
        settingsScene = new SettingsScene();
        setScene(settingsScene);
      }
    }));
  }

  // ---------------------------------------------
  // GETTERS AND SETTERS
  // ---------------------------------------------

  public static SceneManager getInstance() {
    return INSTANCE;
  }

  public SceneType getCurrentSceneType() {
    return currentSceneType;
  }

  public BaseScene getCurrentScene() {
    return currentScene;
  }
}
