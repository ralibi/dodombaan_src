package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
//import com.ralibi.dodombaan.MainActivity.MoveSheepServerMessage;
import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;
import com.ralibi.dodombaan.object.Sheep;

public class GamePlayScene extends BaseScene {
  
  Sprite overlay;
  ButtonSprite pauseButton;
  ButtonSprite resumeButton;
  ButtonSprite exitButton;

  private final int P1_WIN = 1;
  private final int P2_WIN = 2;

  private PhysicsWorld mPhysicsWorld;

  private boolean paused = true;
  private boolean positioning = true;

  // ARENA
  private Sprite arena;

  private Entity nailP1;
  private Entity nailP2;

  private GestureDetector gDetectorP1;
  private GestureDetector gDetectorP2;

  // sheep
  private Sheep sheepP1;
  private Sheep sheepP2;

  // PAUSE SCENE
  private Text winningAnnouncementText;

  private Sprite endRoundOverlay;

  // SCORE BOARD
  private Entity scoreBoard;
  private Text scoreTextP1;
  private Text scoreTextP2;

  
  
  
  @Override
  public void createScene() {
    resetGameData();
    createBackground();
    createMenuPauseScene();
    createMenuRoundOverScene();

    setTouchAreaBindingOnActionDownEnabled(true);

    this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
    registerUpdateHandler(this.mPhysicsWorld);

    createArena();
    createSheep();
    createScoreBoard();

    setPaused(true);
    
    restartSheepPosition();
    
    registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      @Override
      public void onTimePassed(final TimerHandler pTimerHandler) {
        if (isPositioning()) {
          Debug.d("CHECK IS POSITION" + System.currentTimeMillis());
          if (sheepP1.isInPosition() && sheepP2.isInPosition()) {
            sheepPositionReady();
          } else {
            sheepP1.positioning();
            sheepP2.positioning();
          }
        } if (!isPaused()) {
          Debug.d("CHECK OUT" + System.currentTimeMillis());
          if (sheepP1.isOut()) {
            matchOver(P2_WIN);
          } else if (sheepP2.isOut()) {
            matchOver(P1_WIN);
          }
        } 
        // Debug.d("RUNNING_" + System.currentTimeMillis());
        pTimerHandler.reset();
      }
    }));

  }

  private void resetGameData() {
    gameDataManager.p1Score = 0;
    gameDataManager.p2Score = 0;
  }

  private void createScoreBoard() {
    scoreBoard = new Entity(400, 480-40);
    scoreTextP1 = new Text(-40, 0, resourcesManager.fontScore, "0", vbom);
    scoreTextP2 = new Text(40, 0, resourcesManager.fontScore, "0", vbom);
    scoreBoard.attachChild(scoreTextP1);
    scoreBoard.attachChild(scoreTextP2);
    attachChild(scoreBoard);
  }

  private void createArena() {
    arena = new Sprite(400, 240, resourcesManager.gamePlayPlayingArenaRegions.get(gameDataManager.arenaIndex), vbom);
    attachChild(arena);

    nailP1 = new Entity();
    nailP2 = new Entity();
    attachChild(nailP1);
    attachChild(nailP2);

    createNails();
    createNewThread();
  }

  private void createNails() {

    FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

    for (int i = 0; i < resourcesManager.nailNormalRegions.size(); i++) {
      final int _i = i;
      TiledSprite nail_part_1 = new TiledSprite(80, 240 - ((i - 2) * 20), resourcesManager.nailNormalRegions.get(i), vbom) {
        @Override
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

          nailAreaTouched(this, pSceneTouchEvent, nailP1, sheepP1, gDetectorP1, _i);
          return false;
        }
      };
      nailP1.attachChild(nail_part_1);
      registerTouchArea(nail_part_1);
      Body bodyNailP1 = PhysicsFactory.createCircleBody(mPhysicsWorld, nail_part_1, BodyType.StaticBody, FIXTURE_DEF);
      mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nail_part_1, bodyNailP1, true, true));

      TiledSprite nail_part_2 = new TiledSprite(720, 240 - ((i - 2) * 20), resourcesManager.nailNormalRegions.get(i), vbom) {
        @Override
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
          nailAreaTouched(this, pSceneTouchEvent, nailP2, sheepP2, gDetectorP2, _i);
          return false;
        }
      };
      nailP2.attachChild(nail_part_2);
      registerTouchArea(nail_part_2);
      Body bodyNailP2 = PhysicsFactory.createCircleBody(mPhysicsWorld, nail_part_2, BodyType.StaticBody, FIXTURE_DEF);
      mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nail_part_2, bodyNailP2, true, true));
    }
  }

  private void createNewThread() {

    ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

      public void run() {
        gDetectorP1 = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener() {
          public boolean onDoubleTap(MotionEvent e) {
            sheepP1.jumpBackward();
            return true;
          }
        });

        gDetectorP2 = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener() {
          public boolean onDoubleTap(MotionEvent e) {
            sheepP2.jumpBackward();
            return true;
          }
        });
      }
    });

  }

  private void createSheep() {
    sheepP1 = new Sheep(180, 240, gameDataManager.p1SheepIndex, 1);
    sheepP1.createAndAttachSheep(this, mPhysicsWorld, vbom);

    sheepP2 = new Sheep(620, 240, gameDataManager.p2SheepIndex, -1);
    sheepP2.createAndAttachSheep(this, mPhysicsWorld, vbom);
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.gamePlayBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });
  }

  private void createMenuPauseScene() {
    overlay = new Sprite(400, 240, resourcesManager.gamePlayOverlayRegion, vbom);
    attachChild(overlay);

    pauseButton = new ButtonSprite(400, 240 - 80, resourcesManager.gamePlayPauseNormalRegion, resourcesManager.gamePlayPausePressedRegion, resourcesManager.gamePlayPauseDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        pauseGame();
      }
    });
    registerTouchArea(pauseButton);
    attachChild(pauseButton);

    resumeButton = new ButtonSprite(400, 240 - 80, resourcesManager.gamePlayResumeNormalRegion, resourcesManager.gamePlayResumePressedRegion, resourcesManager.gamePlayResumeDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        resumeGame();
      }
    });
    registerTouchArea(resumeButton);
    overlay.attachChild(resumeButton);
    
    exitButton = new ButtonSprite(400, 240 - 160, resourcesManager.gamePlayExitToMenuNormalRegion, resourcesManager.gamePlayExitToMenuPressedRegion, resourcesManager.gamePlayExitToMenuDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadMenuSceneFromGamePlay(engine);
      }
    });
    registerTouchArea(exitButton);
    overlay.attachChild(exitButton);
    
    overlay.setVisible(false);
  }

  private void createMenuRoundOverScene() {
    endRoundOverlay = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, resourcesManager.gamePlayOverlayRegion, vbom) {
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
      }
    };
    winningAnnouncementText = new Text(400, 320, resourcesManager.font, "Player " + gameDataManager.winner + " WON", vbom);
    endRoundOverlay.attachChild(winningAnnouncementText);
  }



  // EVENT
  // ###########################################

  protected void nailAreaTouched(TiledSprite tiledSprite, TouchEvent pSceneTouchEvent, Entity nail, Sheep sheep, GestureDetector gDetector, int index) {
    if (!isPaused()) {
      sheep.moveForward(2 - index);
      resourcesManager.vibrator.vibrate(GameConfigurationManager.VIBRATE_STRENGTH);
      gDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());

      if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {

        for (int j = 0; j < nail.getChildCount(); j++) {
          ((TiledSprite) nail.getChildByIndex(j)).setCurrentTileIndex(0);
        }

        tiledSprite.setCurrentTileIndex(1);
      } else {
        tiledSprite.setCurrentTileIndex(0);
      }
    }
  }

  // GAME STATE ACTION
  // ###########################################

  private void restartSheepPosition() {
    Debug.d("GP RESTART POS");
    setPaused(true);
    if (isPaused()) {
      setPositioning(true);
      sheepP1.restartPosition();
      sheepP2.restartPosition();
    }
  }

  private void sheepPositionReady() {
    Debug.d("GP POSITION READY");
    sheepP1.positionReady();
    sheepP2.positionReady();

    setPositioning(false);
    pauseButton.setVisible(true);
    setPaused(false);

    detachChild(endRoundOverlay);
    unregisterTouchArea(endRoundOverlay);
  }

  protected void pauseGame() {
    if (!isPaused()) {
      engine.getScene().setIgnoreUpdate(true);
      overlay.setVisible(true);
      pauseButton.setVisible(false);
      setPaused(true);
    }
  }

  private void resumeGame() {
    if (isPaused()) {
      overlay.setVisible(false);
      engine.getScene().setIgnoreUpdate(false);
      pauseButton.setVisible(true);
      setPaused(false);
    }
  }

  private void showMenuRoundOverScene() {
    if (!isPaused()) {
      sheepP1.setMatchOver(true);
      sheepP2.setMatchOver(true);

      // setChildScene(menuRoundOverScene, false, true, true);
      pauseButton.setVisible(false);
      setPaused(true);
      winningAnnouncementText.setText("Player " + gameDataManager.winner + " WON");
      
      attachChild(endRoundOverlay);
      registerTouchArea(endRoundOverlay);

        engine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
          public void onTimePassed(final TimerHandler pTimerHandler) {
            engine.unregisterUpdateHandler(pTimerHandler);
            if (isMaxScoreReached()) {
              SceneManager.getInstance().loadMatchOverScene(engine);
            } else {
              Debug.d("restartTimerHandler");
              restartSheepPosition();
            }
          }
        }));
    }
  }

  protected void matchOver(int winner) {
    switch (winner) {
    case P1_WIN:
      gameDataManager.winner = 1;
      gameDataManager.p1Score++;
      break;
    case P2_WIN:
      gameDataManager.winner = 2;
      gameDataManager.p2Score++;
      break;
    default:
      break;
    }
    updateScoreBoard();
    showMenuRoundOverScene();
  }
  
  protected void updateScoreBoard(){
    scoreTextP1.setText("" + gameDataManager.p1Score);
    scoreTextP2.setText("" + gameDataManager.p2Score);
  }

  // GETTER SETTER HELPER
  // ###########################################

  public boolean isPaused() {
    return paused;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public boolean isPositioning() {
    return positioning;
  }

  public void setPositioning(boolean positioning) {
    this.positioning = positioning;
  }
  
  private boolean isMaxScoreReached(){
    return gameDataManager.p1Score == gameDataManager.maxScore || gameDataManager.p2Score == gameDataManager.maxScore;
  }

  protected int getOrientationType(Sprite sprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    int result = (int) Math.floor((double) (pTouchAreaLocalY * 5f / sprite.getHeight()));
    return result - 2;
  }

  // INHERITANCE METHOD
  // ###########################################

  @Override
  public void onBackKeyPressed() {
    if (endRoundOverlay.hasParent()) {
      Debug.d("DO NOTHING");
      // Do Nothing
    } else if (isPaused()) {
      Debug.d("RESUME");
      resumeGame();
    } else {
      Debug.d("PAUSED");
      pauseGame();
    }
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_GAME_PLAY;
  }

  @Override
  public void disposeScene() {
    camera.setHUD(null);
  }

  @Override
  public void unTouchScrollMenu() {
    // TODO Auto-generated method stub
  }
}
