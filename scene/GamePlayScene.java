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
//import com.ralibi.dodombaan.MainActivity.MoveRamServerMessage;
import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;
import com.ralibi.dodombaan.object.Ram;

public class GamePlayScene extends BaseScene {
  
  Entity pauseOverlay;
  ButtonSprite pauseButton;
  ButtonSprite resumeButton;
  ButtonSprite exitButton;

  private final int P1_WIN = 1;
  private final int P2_WIN = 2;

  private PhysicsWorld mPhysicsWorld;

  private boolean paused = true;
  private boolean positioning = true;

  // ARENA
  private Sprite rubber;
  private Sprite rubberVibrating;
  private Sprite rubberShadow;
  private Sprite rubberVibratingShadow;

  private Entity nailP1;
  private Entity nailP2;

  private GestureDetector gDetectorP1;
  private GestureDetector gDetectorP2;

  // ram
  private Ram ramP1;
  private Ram ramP2;

  // PAUSE SCENE
  private Text winningAnnouncementText;

  private Sprite endRoundOverlay;

  // SCORE BOARD
  private Entity scoreBoard;
  private Entity scoreP1;
  private Entity scoreP2;

  
  
  
  @Override
  public void createScene() {
    resetGameData();
    createBackground();
    createToolbar();

    setTouchAreaBindingOnActionDownEnabled(true);

    this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
    registerUpdateHandler(this.mPhysicsWorld);

    createArena();
    createRam();
    createScoreBoard();
    
    createMenuPauseScene();
    createMenuRoundOverScene();

    setPaused(true);
    
    restartRamPosition();
    
    registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
      @Override
      public void onTimePassed(final TimerHandler pTimerHandler) {
        if (isPositioning()) {
          Debug.d("CHECK IS POSITION" + System.currentTimeMillis());
          if (ramP1.isInPosition() && ramP2.isInPosition()) {
            ramPositionReady();
          } else {
            ramP1.positioning();
            ramP2.positioning();
          }
        } if (!isPaused()) {
          if (ramP1.isOut()) {
          	ramP1.showDrop(true);
            matchOver(P2_WIN);
          } else if (ramP2.isOut()) {
          	ramP2.showDrop(true);
            matchOver(P1_WIN);
          }
        } 
        // Debug.d("RUNNING_" + System.currentTimeMillis());
        pTimerHandler.reset();
      }
    }));

    // playSound(BGM_MUSIC);
  }

  private void createToolbar() {
	  attachChild(new Sprite(400, 32, resourcesManager.bannerBottomBackgroundRegion, vbom));
	  Sprite bannerTop = new Sprite(400, 480-32, resourcesManager.bannerBottomBackgroundRegion, vbom);
	  bannerTop.setFlippedVertical(true);
	  attachChild(bannerTop);
  }

	private void resetGameData() {
    gameDataManager.p1Score = 0;
    gameDataManager.p2Score = 0;
  }

  private void createScoreBoard() {
    scoreBoard = new Entity(400, 480-32);
    
    int dfc = 360; // distance_from_center
    int tabs = 36;

    scoreP1 = new Entity(-dfc + tabs, -10);
    scoreP2 = new Entity(dfc - tabs, -10);
    
    Text p1Name = new Text(-dfc, 15, resourcesManager.fontScore, GameConfigurationManager.RAM_NAME[gameDataManager.p1RamIndex], vbom);
    p1Name.setX(p1Name.getX() + p1Name.getWidth()/2 + tabs);
    scoreBoard.attachChild(p1Name);
    Text p2Name = new Text(dfc, 15, resourcesManager.fontScore, GameConfigurationManager.RAM_NAME[gameDataManager.p2RamIndex], vbom);
    p2Name.setX(p2Name.getX() - p2Name.getWidth()/2 - tabs);
    scoreBoard.attachChild(p2Name);

    Sprite p1Thumb = new Sprite(-dfc, 0, resourcesManager.gamePlayRamThumbRegions.get(gameDataManager.p1RamIndex), vbom);
    scoreBoard.attachChild(p1Thumb);
    Sprite p2Thumb = new Sprite(dfc, 0, resourcesManager.gamePlayRamThumbRegions.get(gameDataManager.p2RamIndex), vbom);
    scoreBoard.attachChild(p2Thumb);
    
    scoreBoard.attachChild(scoreP1);
    scoreBoard.attachChild(scoreP2);
    
    attachChild(scoreBoard);
    updateScoreBoard();
  }

  private void createArena() {
    rubberShadow = new Sprite(400 - 23, 240 - 54, resourcesManager.gamePlayRubberShadowRegion, vbom);
    attachChild(rubberShadow);
    rubberVibratingShadow = new Sprite(400 - 23, 240 - 54, resourcesManager.gamePlayRubberVibratingShadowRegion, vbom);
    attachChild(rubberVibratingShadow);
    rubberVibratingShadow.setAlpha(0.3f);
    rubberShadow.setAlpha(0.3f);
    
    
    rubber = new Sprite(400, 240, resourcesManager.gamePlayRubberRegions.get(gameDataManager.arenaIndex), vbom);
    attachChild(rubber);
    rubberVibrating = new Sprite(400, 240, resourcesManager.gamePlayRubberVibratingRegions.get(gameDataManager.arenaIndex), vbom);
    attachChild(rubberVibrating);
    
    vibrateRubber(false);

    nailP1 = new Entity();
    nailP2 = new Entity();
    attachChild(nailP1);
    attachChild(nailP2);

    createNails();
    createNewThread();
  }

  private void createNails() {

    FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

    int nailSize = 120;
    
    for (int i = 0; i < resourcesManager.nailNormalRegions.size(); i++) {
      final int _i = i;
      TiledSprite nail_part_1 = new TiledSprite(80, 240 - ((i - 2) * nailSize/5), resourcesManager.nailNormalRegions.get(i), vbom) {
        @Override
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

          nailAreaTouched(this, pSceneTouchEvent, nailP1, ramP1, gDetectorP1, _i);
          return false;
        }
      };
      nailP1.attachChild(nail_part_1);
      registerTouchArea(nail_part_1);
      Body bodyNailP1 = PhysicsFactory.createCircleBody(mPhysicsWorld, nail_part_1, BodyType.StaticBody, FIXTURE_DEF);
      mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nail_part_1, bodyNailP1, true, true));

      TiledSprite nail_part_2 = new TiledSprite(720, 240 - ((i - 2) * nailSize/5), resourcesManager.nailNormalRegions.get(i), vbom) {
        @Override
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
          nailAreaTouched(this, pSceneTouchEvent, nailP2, ramP2, gDetectorP2, _i);
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
            ramP1.jumpBackward();
            return true;
          }
        });

        gDetectorP2 = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener() {
          public boolean onDoubleTap(MotionEvent e) {
            ramP2.jumpBackward();
            return true;
          }
        });
      }
    });

  }

  private void createRam() {
    ramP1 = new Ram(180, 240, gameDataManager.p1RamIndex, 1);
    ramP1.createAndAttachRam(this, mPhysicsWorld, vbom);

    ramP2 = new Ram(620, 240, gameDataManager.p2RamIndex, -1);
    ramP2.createAndAttachRam(this, mPhysicsWorld, vbom);
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.gamePlayPlayingBackgroundRegions.get(gameDataManager.arenaIndex), vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });
  }

  private void createMenuPauseScene() {
    pauseOverlay = new Entity(400, 240, 800, 480);
    pauseOverlay.attachChild(new Sprite(400, 240, resourcesManager.pauseOverlayRegion, vbom));
    
    attachChild(pauseOverlay);

    pauseButton = new ButtonSprite(400, 32, resourcesManager.pauseButtonRegions[0], resourcesManager.pauseButtonRegions[1], resourcesManager.pauseButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        pauseGame();
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(pauseButton);
    attachChild(pauseButton);

    resumeButton = new ButtonSprite(400, 240 - 80, resourcesManager.resumeButtonRegions[0], resourcesManager.resumeButtonRegions[1], resourcesManager.resumeButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        resumeGame();
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(resumeButton);
    pauseOverlay.attachChild(resumeButton);
    
    exitButton = new ButtonSprite(400, 240 - 160, resourcesManager.quitButtonRegions[0], resourcesManager.quitButtonRegions[1], resourcesManager.quitButtonRegions[2], vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
      	SceneManager.getInstance().changeScene(SceneType.SCENE_GAME_PLAY, SceneType.SCENE_MAIN_MENU);
        playSound(CLICK_MUSIC);
      }
    });
    registerTouchArea(exitButton);
    pauseOverlay.attachChild(exitButton);
    
    pauseOverlay.setVisible(false);
  }

  private void createMenuRoundOverScene() {
    endRoundOverlay = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, resourcesManager.overlayRegion, vbom) {
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
      }
    };
    winningAnnouncementText = new Text(400, 320, resourcesManager.font, "Player " + gameDataManager.winner + " WON", vbom);
    endRoundOverlay.attachChild(winningAnnouncementText);
  }



  // EVENT
  // ###########################################

  protected void nailAreaTouched(TiledSprite tiledSprite, TouchEvent pSceneTouchEvent, Entity nail, Ram ram, GestureDetector gDetector, int index) {
    if (!isPaused()) {
      ram.moveForward(2 - index);
      vibrate(GameConfigurationManager.VIBRATE_STRENGTH);
      gDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());

      if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {

        for (int j = 0; j < nail.getChildCount(); j++) {
          ((TiledSprite) nail.getChildByIndex(j)).setCurrentTileIndex(0);
        }

        tiledSprite.setCurrentTileIndex(1);
        vibrateRubber(true);
      } else {
        tiledSprite.setCurrentTileIndex(0);
        vibrateRubber(false);
      }
    }
  }

  // GAME STATE ACTION
  // ###########################################

  private void restartRamPosition() {
    Debug.d("GP RESTART POS");
    setPaused(true);
    if (isPaused()) {
      setPositioning(true);
      ramP1.restartPosition();
      ramP2.restartPosition();
    }
  }

  private void ramPositionReady() {
    Debug.d("GP POSITION READY");
    ramP1.positionReady();
    ramP2.positionReady();

    setPositioning(false);
    pauseButton.setVisible(true);
    setPaused(false);

    detachChild(endRoundOverlay);
    unregisterTouchArea(endRoundOverlay);
    playSound(SHORT_WHISTLE_MUSIC);
    playMusic();
  }

  protected void pauseGame() {
    if (!isPaused()) {
      engine.getScene().setIgnoreUpdate(true);
      pauseOverlay.setVisible(true);
      pauseButton.setVisible(false);
      pauseMusic();
      setPaused(true);
    }
  }

  private void resumeGame() {
    if (isPaused()) {
      pauseOverlay.setVisible(false);
      engine.getScene().setIgnoreUpdate(false);
      pauseButton.setVisible(true);
      playMusic();
      setPaused(false);
    }
  }

  private void showMenuRoundOverScene() {
    if (!isPaused()) {
      ramP1.setMatchOver(true);
      ramP2.setMatchOver(true);

      // setChildScene(menuRoundOverScene, false, true, true);
      pauseButton.setVisible(false);
      setPaused(true);
      winningAnnouncementText.setText("Player " + gameDataManager.winner + " WON");
      

      stopMusic();
      if (isMaxScoreReached()) {
        playSound(LONG_WHISTLE_MUSIC);
        playSound(CLAPPING_HAND_MUSIC);
      } else {
        playSound(SHORT_WHISTLE_MUSIC);
      }
      
      attachChild(endRoundOverlay);
      registerTouchArea(endRoundOverlay);

        engine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
          public void onTimePassed(final TimerHandler pTimerHandler) {
            engine.unregisterUpdateHandler(pTimerHandler);
            if (isMaxScoreReached()) {
            	SceneManager.getInstance().changeScene(SceneType.SCENE_GAME_PLAY, SceneType.SCENE_MATCH_OVER);
            } else {
              restartRamPosition();
            }
          }
        }));
        
        vibrateRubber(false);
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
    scoreP1.detachChildren();
    for (int i = 0; i < gameDataManager.p1Score; i++) {
	    scoreP1.attachChild(new Sprite(i*28 + 12, 0, resourcesManager.starFilled, vbom));
    }
    for (int i = gameDataManager.p1Score; i < gameDataManager.getTotalMaximumScore(); i++) {
	    scoreP1.attachChild(new Sprite(i*28 + 12, 0, resourcesManager.starBlank, vbom));
    }

    scoreP2.detachChildren();
    for (int i = 0; i < gameDataManager.p2Score; i++) {
    	scoreP2.attachChild(new Sprite(-i*28 - 12, 0, resourcesManager.starFilled, vbom));
    }
    for (int i = gameDataManager.p2Score; i < gameDataManager.getTotalMaximumScore(); i++) {
    	scoreP2.attachChild(new Sprite(-i*28 - 12, 0, resourcesManager.starBlank, vbom));
    }
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
    return gameDataManager.p1Score == gameDataManager.getTotalMaximumScore() || gameDataManager.p2Score == gameDataManager.getTotalMaximumScore();
  }

  protected int getOrientationType(Sprite sprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    int result = (int) Math.floor((double) (pTouchAreaLocalY * 5f / sprite.getHeight()));
    return result - 2;
  }
  
  public void vibrateRubber(boolean vib){
    rubber.setVisible(!vib);
    rubberVibrating.setVisible(vib);
    rubberShadow.setVisible(!vib);
    rubberVibratingShadow.setVisible(vib);
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
