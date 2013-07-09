package com.ralibi.dodombaan.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.scene.CareerMenuScene;
import com.ralibi.dodombaan.scene.ChampionshipMenuScene;
import com.ralibi.dodombaan.scene.ClientMenuScene;
import com.ralibi.dodombaan.scene.ExerciseMenuScene;
import com.ralibi.dodombaan.scene.GamePlayScene;
import com.ralibi.dodombaan.scene.HostMenuScene;
import com.ralibi.dodombaan.scene.LoadingScene;
import com.ralibi.dodombaan.scene.MainMenuScene;
import com.ralibi.dodombaan.scene.MatchOverScene;
import com.ralibi.dodombaan.scene.MatchSettingsScene;
import com.ralibi.dodombaan.scene.MultiplayerBluetoothMenuScene;
import com.ralibi.dodombaan.scene.MultiplayerChampionshipMenuScene;
import com.ralibi.dodombaan.scene.MultiplayerMenuScene;
import com.ralibi.dodombaan.scene.MultiplayerSingleDeviceMenuScene;
import com.ralibi.dodombaan.scene.SettingsScene;
import com.ralibi.dodombaan.scene.RamSelectionScene;
import com.ralibi.dodombaan.scene.SplashScene;

public class SceneManager {
  // ---------------------------------------------
  // SCENES
  // ---------------------------------------------

  private BaseScene splashScene;
  private BaseScene menuScene;
  private BaseScene ramSelectionScene;
  private BaseScene matchSettingsScene;
  private BaseScene settingsScene;
  private BaseScene matchOverScene;
  private BaseScene gamePlayScene;
  private BaseScene loadingScene;
  private BaseScene careerMenuScene;
  
  private BaseScene multiplayerMenuScene;
  private BaseScene multiplayerSingleDeviceMenuScene;
  private BaseScene multiplayerBluetoothMenuScene;
  private BaseScene multiplayerChampionshipMenuScene;

  private BaseScene championshipKnockoutScene;
  private BaseScene championshipListScene;
  private BaseScene championshipMenuScene;
  private BaseScene championshipRoundRobinScene;
  private BaseScene championshipTypeMenuScene;

  private BaseScene exerciseMenuScene;
  private BaseScene fixtureMenuScene;
  private BaseScene invitationStatusScene;

  private BaseScene myRamsScene;
  private BaseScene playersAdditionScene;
  private BaseScene ramUpgradingScene;
  private BaseScene selectedChampionshipScene;

  private BaseScene hostMenuScene, clientMenuScene;
  
  public SceneType prevSceneType;
  public SceneType nextSceneType;

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------

  private static final SceneManager INSTANCE = new SceneManager();

  private SceneType currentSceneType = SceneType.SCENE_SPLASH;

  private BaseScene currentScene;

  private Engine engine = ResourcesManager.getInstance().engine;

  public enum SceneType {
    SCENE_SPLASH, 
    SCENE_MAIN_MENU, 
    SCENE_GAME_PLAY, 
    SCENE_LOADING, 
    SCENE_SETTINGS, 
    SCENE_RAM_SELECTION, 
    SCENE_MATCH_SETTINGS, 
    SCENE_MULTIPLAYER_MENU,
    SCENE_MULTIPLAYER_SINGLE_DEVICE_MENU,
    SCENE_MULTIPLAYER_BLUETOOTH_MENU,
    SCENE_MULTIPLAYER_CHAMPIONSHIP_MENU,
    SCENE_MATCH_OVER, 
    SCENE_CAREER_MENU, 
    SCENE_CHAMPIONSHIP_KNOCKOUT, 
    SCENE_CHAMPIONSHIP_LIST, 
    SCENE_CHAMPIONSHIP_MENU, 
    SCENE_CHAMPIONSHIP_ROUND_ROBIN, 
    SCENE_CHAMPIONSHIP_TYPE_MENU, 
    SCENE_EXERCISE_MENU, 
    SCENE_FIXTURE_MENU, 
    SCENE_INVITATION_STATUS, 
    SCENE_MY_RAMS, 
    SCENE_PLAYERS_ADDITION, 
    SCENE_RAM_UPGRADING, 
    SCENE_SELECTED_CHAMPIONSHIP,
    SCENE_HOST_MENU,
    SCENE_CLIENT_MENU,
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
      case SCENE_MAIN_MENU:
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
      case SCENE_MULTIPLAYER_MENU:
        setScene(multiplayerMenuScene);
        break;
      case SCENE_MULTIPLAYER_SINGLE_DEVICE_MENU:
        setScene(multiplayerSingleDeviceMenuScene);
        break;
      case SCENE_MULTIPLAYER_BLUETOOTH_MENU:
        setScene(multiplayerBluetoothMenuScene);
        break;
      case SCENE_MULTIPLAYER_CHAMPIONSHIP_MENU:
        setScene(multiplayerChampionshipMenuScene);
        break;
      case SCENE_RAM_SELECTION:
        setScene(ramSelectionScene);
        break;
      case SCENE_MATCH_OVER:
        setScene(matchOverScene);
        break;
      case SCENE_CAREER_MENU:
        setScene(careerMenuScene);
        break;

      case SCENE_CHAMPIONSHIP_KNOCKOUT:
        setScene(championshipKnockoutScene);
        break;
      case SCENE_CHAMPIONSHIP_LIST:
        setScene(championshipListScene);
        break;
      case SCENE_CHAMPIONSHIP_MENU:
        setScene(championshipMenuScene);
        break;
      case SCENE_CHAMPIONSHIP_ROUND_ROBIN:
        setScene(championshipRoundRobinScene);
        break;
      case SCENE_CHAMPIONSHIP_TYPE_MENU:
        setScene(championshipTypeMenuScene);
        break;
      case SCENE_EXERCISE_MENU:
        setScene(exerciseMenuScene);
        break;
      case SCENE_FIXTURE_MENU:
        setScene(fixtureMenuScene);
        break;
      case SCENE_INVITATION_STATUS:
        setScene(invitationStatusScene);
        break;
      case SCENE_MY_RAMS:
        setScene(myRamsScene);
        break;
      case SCENE_PLAYERS_ADDITION:
        setScene(playersAdditionScene);
        break;
      case SCENE_RAM_UPGRADING:
        setScene(ramUpgradingScene);
        break;
      case SCENE_SELECTED_CHAMPIONSHIP:
        setScene(selectedChampionshipScene);
        break;
        

      case SCENE_HOST_MENU:
        setScene(hostMenuScene);
        break;
      case SCENE_CLIENT_MENU:
        setScene(clientMenuScene);
        break;

      default:
        break;
    }
  }
  
  public void changeScene(SceneType prevSceneType, SceneType nextSceneType){
  	this.prevSceneType = prevSceneType;
  	this.nextSceneType = nextSceneType;
  	
    switch (prevSceneType) {
      case SCENE_MAIN_MENU:
      	unloadMainMenuScene();
        break;
      case SCENE_GAME_PLAY:
      	unloadGamePlayScene();
        break;
      case SCENE_SPLASH:
        break;
      case SCENE_LOADING:
        break;
      case SCENE_SETTINGS:
      	unloadSettingsScene();
        break;
      case SCENE_MATCH_SETTINGS:
      	unloadMatchSettingsScene();
        break;
      case SCENE_MULTIPLAYER_MENU:
      	unloadMultiplayerMenuScene();
        break;
      case SCENE_MULTIPLAYER_SINGLE_DEVICE_MENU:
      	unloadMultiplayerSingleDeviceMenuScene();
        break;
      case SCENE_MULTIPLAYER_BLUETOOTH_MENU:
      	unloadMultiplayerBluetoothMenuScene();
        break;
      case SCENE_MULTIPLAYER_CHAMPIONSHIP_MENU:
      	unloadMultiplayerChampionshipMenuScene();
        break;
      case SCENE_RAM_SELECTION:
      	unloadRamSelectionScene();
        break;
      case SCENE_MATCH_OVER:
      	unloadMatchOverScene();
        break;
      case SCENE_CAREER_MENU:
      	unloadCareerMenuScene();
        break;
      case SCENE_CHAMPIONSHIP_KNOCKOUT:
        break;
      case SCENE_CHAMPIONSHIP_LIST:
        break;
      case SCENE_CHAMPIONSHIP_MENU:
      	unloadChampionshipMenuScene();
        break;
      case SCENE_CHAMPIONSHIP_ROUND_ROBIN:
        break;
      case SCENE_CHAMPIONSHIP_TYPE_MENU:
        break;
      case SCENE_EXERCISE_MENU:
      	unloadExerciseMenuScene();
        break;
      case SCENE_FIXTURE_MENU:
        break;
      case SCENE_INVITATION_STATUS:
        break;
      case SCENE_MY_RAMS:
        break;
      case SCENE_PLAYERS_ADDITION:
        break;
      case SCENE_RAM_UPGRADING:
        break;
      case SCENE_SELECTED_CHAMPIONSHIP:
        break;
      case SCENE_HOST_MENU:
      	unloadHostMenuScene();
        break;
      case SCENE_CLIENT_MENU:
      	unloadClientMenuScene();
        break;
      default:
        break;
    }


    switch (nextSceneType) {
      case SCENE_MAIN_MENU:
      	loadMainMenuScene(engine);
        break;
      case SCENE_GAME_PLAY:
      	loadGamePlayScene(engine);
        break;
      case SCENE_SPLASH:
        break;
      case SCENE_LOADING:
        break;
      case SCENE_SETTINGS:
      	loadSettingsScene(engine);
        break;
      case SCENE_MATCH_SETTINGS:
      	loadMatchSettingsScene(engine);
        break;
      case SCENE_MULTIPLAYER_MENU:
      	loadMultiplayerMenuScene(engine);
        break;
      case SCENE_MULTIPLAYER_SINGLE_DEVICE_MENU:
      	loadMultiplayerSingleDeviceMenuScene(engine);
        break;
      case SCENE_MULTIPLAYER_BLUETOOTH_MENU:
      	loadMultiplayerBluetoothMenuScene(engine);
        break;
      case SCENE_MULTIPLAYER_CHAMPIONSHIP_MENU:
      	loadMultiplayerChampionshipMenuScene(engine);
        break;
      case SCENE_RAM_SELECTION:
      	loadRamSelectionScene(engine);
        break;
      case SCENE_MATCH_OVER:
      	loadMatchOverScene(engine);
        break;
      case SCENE_CAREER_MENU:
      	loadCareerMenuScene(engine);
        break;
      case SCENE_CHAMPIONSHIP_KNOCKOUT:
        break;
      case SCENE_CHAMPIONSHIP_LIST:
        break;
      case SCENE_CHAMPIONSHIP_MENU:
      	loadChampionshipMenuScene(engine);
        break;
      case SCENE_CHAMPIONSHIP_ROUND_ROBIN:
        break;
      case SCENE_CHAMPIONSHIP_TYPE_MENU:
        break;
      case SCENE_EXERCISE_MENU:
      	loadExerciseMenuScene(engine);
        break;
      case SCENE_FIXTURE_MENU:
        break;
      case SCENE_INVITATION_STATUS:
        break;
      case SCENE_MY_RAMS:
        break;
      case SCENE_PLAYERS_ADDITION:
        break;
      case SCENE_RAM_UPGRADING:
        break;
      case SCENE_SELECTED_CHAMPIONSHIP:
        break;
      case SCENE_HOST_MENU:
      	loadHostMenuScene(engine);
        break;
      case SCENE_CLIENT_MENU:
      	loadClientMenuScene(engine);
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
  
  

  public void createMainMenuScene() {
    ResourcesManager.getInstance().loadMenuResources();
    menuScene = new MainMenuScene();
    loadingScene = new LoadingScene();
    setScene(menuScene);
    disposeSplashScene();
  }

  
  private void loadMainMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMenuTextures();
        setScene(menuScene);
      }
    }));
  }
  
  private void unloadMainMenuScene() {
    setScene(loadingScene);
    menuScene.disposeScene();
    ResourcesManager.getInstance().unloadMenuTextures();
  }
  

  private void loadRamSelectionScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadRamSelectionResources();
        ramSelectionScene = new RamSelectionScene();
        setScene(ramSelectionScene);
      }
    }));
  }
  
  private void unloadRamSelectionScene() {
    setScene(loadingScene);
    ramSelectionScene.disposeScene();
    ResourcesManager.getInstance().unloadRamSelectionTextures();
  }


  private void loadSettingsScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadSettingsResources();
        settingsScene = new SettingsScene();
        setScene(settingsScene);
      }
    }));
  }
  
  private void unloadSettingsScene() {
    setScene(loadingScene);
    settingsScene.disposeScene();
    ResourcesManager.getInstance().unloadSettingsTextures();
  }

  
  private void loadMatchSettingsScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMatchSettingsResources();
        matchSettingsScene = new MatchSettingsScene();
        setScene(matchSettingsScene);
      }
    }));
  }
  
  private void unloadMatchSettingsScene() {
    setScene(loadingScene);
    matchSettingsScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchSettingsTextures();
  }


  private void loadCareerMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadCareerMenuResources();
        careerMenuScene = new CareerMenuScene();
        setScene(careerMenuScene);
      }
    }));
  }
  
  private void unloadCareerMenuScene() {
    setScene(loadingScene);
    careerMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadCareerMenuTextures();
  }

  
  private void loadGamePlayScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadGamePlayResources();
        gamePlayScene = new GamePlayScene();
        setScene(gamePlayScene);
      }
    }));
  }

  private void unloadGamePlayScene() {
    setScene(loadingScene);
    gamePlayScene.stopMusic();
    gamePlayScene.disposeScene();
    ResourcesManager.getInstance().unloadGamePlayTextures();
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

  
  private void loadMatchOverScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMatchOverResources();
        matchOverScene = new MatchOverScene();
        setScene(matchOverScene);
      }
    }));
  }

  private void unloadMatchOverScene() {
    setScene(loadingScene);
    matchOverScene.disposeScene();
    ResourcesManager.getInstance().unloadMatchOverTextures();
  }
  

  private void loadExerciseMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadExerciseMenuResources();
        exerciseMenuScene = new ExerciseMenuScene();
        setScene(exerciseMenuScene);
      }
    }));
  }

  private void unloadExerciseMenuScene() {
    setScene(loadingScene);
    exerciseMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadExerciseMenuTextures();
  }
  

  private void loadChampionshipMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadChampionshipMenuResources();
        championshipMenuScene = new ChampionshipMenuScene();
        setScene(championshipMenuScene);
      }
    }));
  }

  private void unloadChampionshipMenuScene() {
    setScene(loadingScene);
    championshipMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadChampionshipMenuTextures();
  }
  

  private void loadMultiplayerMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMultiplayerMenuResources();
        multiplayerMenuScene = new MultiplayerMenuScene();
        setScene(multiplayerMenuScene);
      }
    }));
  }

  private void unloadMultiplayerMenuScene() {
    setScene(loadingScene);
    multiplayerMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadMultiplayerMenuTextures();
  }
  

  private void loadMultiplayerSingleDeviceMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMultiplayerSingleDeviceMenuResources();
        multiplayerSingleDeviceMenuScene = new MultiplayerSingleDeviceMenuScene();
        setScene(multiplayerSingleDeviceMenuScene);
      }
    }));
  }

  private void unloadMultiplayerSingleDeviceMenuScene() {
    setScene(loadingScene);
    multiplayerSingleDeviceMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadMultiplayerSingleDeviceMenuTextures();
  }
  

  private void loadMultiplayerBluetoothMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMultiplayerBluetoothMenuResources();
        multiplayerBluetoothMenuScene = new MultiplayerBluetoothMenuScene();
        setScene(multiplayerBluetoothMenuScene);
      }
    }));
  }

  private void unloadMultiplayerBluetoothMenuScene() {
    setScene(loadingScene);
    multiplayerBluetoothMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadMultiplayerBluetoothMenuTextures();
  }
  

  private void loadMultiplayerChampionshipMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadMultiplayerChampionshipMenuResources();
        multiplayerChampionshipMenuScene = new MultiplayerChampionshipMenuScene();
        setScene(multiplayerChampionshipMenuScene);
      }
    }));
  }

  private void unloadMultiplayerChampionshipMenuScene() {
    setScene(loadingScene);
    multiplayerChampionshipMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadMultiplayerChampionshipMenuTextures();
  }

  
  private void loadHostMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadHostMenuResources();
        hostMenuScene = new HostMenuScene();
        setScene(hostMenuScene);
      }
    }));
  }

  private void unloadHostMenuScene() {
    setScene(loadingScene);
    hostMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadHostMenuTextures();
  }
  


  private void loadClientMenuScene(final Engine mEngine) {
    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      public void onTimePassed(final TimerHandler pTimerHandler) {
        mEngine.unregisterUpdateHandler(pTimerHandler);
        ResourcesManager.getInstance().loadClientMenuResources();
        clientMenuScene = new ClientMenuScene();
        setScene(clientMenuScene);
      }
    }));
  }
  
  private void unloadClientMenuScene() {
    setScene(loadingScene);
    clientMenuScene.disposeScene();
    ResourcesManager.getInstance().unloadClientMenuTextures();
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
