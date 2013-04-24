package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
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

public class GamePlayScene extends BaseScene implements IOnMenuItemClickListener {

	private final int PAUSE = 0;
	private final int RESUME = 1;
	private final int EXIT_TO_MENU= 2;
	
	private final int NEXT = 3;
	private final int NEXT_ROUND = 4;

	private final int P1_WIN = 1;
	private final int P2_WIN = 2;
	
	
	private PhysicsWorld mPhysicsWorld;

	private boolean paused = true;
	private boolean positioning = true;

	private Sheep sheepP1;
	private Sheep sheepP2;
	private Sprite arena;
	
	private Entity nailP1;
	private Entity nailP2;
	
	private Sprite pauseButton;
	private MenuScene menuPauseScene;
	private Text winningAnnouncementText;
	

	private GestureDetector gDetectorP1;
	private GestureDetector gDetectorP2;
	// private ScrollMenu gamePlayHUD;
	
	private Sprite endRoundOverlay;
	
	
	
	
	@Override
	public void createScene() {
		createBackground();
		createPauseButton();
		createMenuPauseScene();
		createMenuRoundOverScene();
		
		setTouchAreaBindingOnActionDownEnabled(true);

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		registerUpdateHandler(this.mPhysicsWorld);
		
		createArena();
		createSheep();

		setPaused(true);
		restartPosition();
		
		registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				if(!isPaused()){
					Debug.d("CHECK OUT" + System.currentTimeMillis());
					if(sheepP1.isOut()){
						matchOver(P2_WIN);
					}
					else if(sheepP2.isOut()){
						matchOver(P1_WIN);
					}
				}
				else if(isPositioning()){
					Debug.d("CHECK IS POSITION" + System.currentTimeMillis());
					if(sheepP1.isInPosition() && sheepP2.isInPosition()){
						positionReady();
					}
					else{
						sheepP1.positioning();
						sheepP2.positioning();
					}
				}
				// Debug.d("RUNNING_" + System.currentTimeMillis());
				pTimerHandler.reset();
			}                      
		}));
		
	}

	protected void matchOver(int winner) {
		switch (winner) {
		case P1_WIN:	
			gameDataManager.winner = 1;
			break;
		case P2_WIN:
			gameDataManager.winner = 2;
			break;
		default:
			break;
		}
		showMenuRoundOverScene();
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
			TiledSprite nail_part_1 = new TiledSprite(80, 240 - ((i - 2) * 20), resourcesManager.nailNormalRegions.get(i), vbom){
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
			
			TiledSprite nail_part_2 = new TiledSprite(720, 240 - ((i - 2) * 20), resourcesManager.nailNormalRegions.get(i), vbom){
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
	
	

	protected void nailAreaTouched(TiledSprite tiledSprite, TouchEvent pSceneTouchEvent, Entity nail, Sheep sheep, GestureDetector gDetector, int index) {
		if(!isPaused()){
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

	private void createNewThread() {

		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

			public void run() {
				gDetectorP1 = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener(){
					public boolean onDoubleTap(MotionEvent e)
					{     
						sheepP1.jumpBackward();
						return true;
					}
				});

				gDetectorP2 = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener(){
					public boolean onDoubleTap(MotionEvent e)
					{     
						sheepP2.jumpBackward();
						return true;
					}
				});
			}
		});
		
	}
	

	protected int getOrientationType(Sprite sprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		int result = (int) Math.floor((double) (pTouchAreaLocalY * 5f / sprite.getHeight()) );
		return result - 2;
	}

	private void createSheep() {
		
		sheepP1 = new Sheep(180, 240, gameDataManager.p1SheepIndex, 1);
		sheepP1.createAndAttachSheep(this, mPhysicsWorld, vbom);
		
		sheepP2 = new Sheep(620, 240, gameDataManager.p2SheepIndex, -1);
		sheepP2.createAndAttachSheep(this, mPhysicsWorld, vbom);
		
	}

	private void createBackground() {
		attachChild(new Sprite(400,  240, resourcesManager.gamePlayBackgroundRegion, vbom)
		{
			@Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		});
	}
	
	private void createMenuPauseScene() {
		menuPauseScene = new MenuScene(camera);
		menuPauseScene.setPosition(400, 240);

		final Sprite overlay = new Sprite(0, 0, resourcesManager.gamePlayOverlayRegion, vbom);
	    final IMenuItem resumeItem = new ScaleMenuItemDecorator(new SpriteMenuItem(RESUME, resourcesManager.gamePlayResumeRegion, vbom), 1.2f, 1);
	    final IMenuItem exitToMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(EXIT_TO_MENU, resourcesManager.gamePlayExitToMenuRegion, vbom), 1.2f, 1);
	    	    
	    menuPauseScene.attachChild(overlay);
	    menuPauseScene.addMenuItem(resumeItem);
	    menuPauseScene.addMenuItem(exitToMenuItem);
	    
	    menuPauseScene.buildAnimations();
	    menuPauseScene.setBackgroundEnabled(false);

	    resumeItem.setPosition(0, -80);
	    exitToMenuItem.setPosition(0, -160);
	    
	    menuPauseScene.setOnMenuItemClickListener(this);
	}
	

	private void createMenuRoundOverScene() {
		endRoundOverlay = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, resourcesManager.gamePlayOverlayRegion, vbom){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				return true;
			}
		};
		winningAnnouncementText = new Text(400, 320, resourcesManager.font, "Player " + gameDataManager.winner + " WON", vbom);
		endRoundOverlay.attachChild(winningAnnouncementText);
	}
	
	private void createPauseButton()
	{
		pauseButton = new Sprite(400, 160, resourcesManager.gamePlayPauseRegion, vbom){	
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pauseGame();
				return true;
			}
		};
		
		attachChild(pauseButton);
		registerTouchArea(pauseButton);
	}
	
	
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
	

	@Override
	public void onBackKeyPressed() {
		if(isPaused()){
			resumeGame();
		}
		else{
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
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case PAUSE:
			// SceneManager.getInstance().loadSheepSelectionSceneFromMatchSettings(engine);
			return true;
		case RESUME:			
			resumeGame();
			return true;
		case EXIT_TO_MENU:
			SceneManager.getInstance().loadMenuSceneFromGamePlay(engine);
			return true;
		case NEXT:
			SceneManager.getInstance().loadMatchOverScene(engine);
			return true;
		case NEXT_ROUND:
			restartPosition();
			return true;
		default:
			return false;
		}
	}

	private void showMenuRoundOverScene() {
		if(!isPaused()){
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
	            	Debug.d("After Handler");
	            	restartPosition();
	            }
	        }));
		}
	}
	
	
	
	private void restartPosition() {
		Debug.d("GP RESTART POS");
		setPaused(true);
		if(isPaused()){
			setPositioning(true);
			sheepP1.restartPosition();
			sheepP2.restartPosition();
		}
	}
	
	private void positionReady(){
		
		Debug.d("GP POSITION READY");
		
		setPositioning(false);
	    pauseButton.setVisible(true);
	    setPaused(false);

	    detachChild(endRoundOverlay);
		unregisterTouchArea(endRoundOverlay);
		sheepP1.positionReady();
		sheepP2.positionReady();
	}

	protected void pauseGame() {
		if(!isPaused()){
			engine.getScene().setIgnoreUpdate(true);
		    setChildScene(menuPauseScene, false, true, true);
		    pauseButton.setVisible(false);
		    setPaused(true);
		}
	}

	private void resumeGame() {
		if(isPaused()){
		    clearChildScene();
			engine.getScene().setIgnoreUpdate(false);
		    pauseButton.setVisible(true);
		    setPaused(false);
		}
	}

	@Override
	public void unTouchScrollMenu() {
		// TODO Auto-generated method stub
	}

}
