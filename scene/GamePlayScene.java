package com.ralibi.dodombaan.scene;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.State;
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
import com.ralibi.dodombaan.MainActivity;
//import com.ralibi.dodombaan.MainActivity.MoveSheepServerMessage;
import com.ralibi.dodombaan.base.BaseScene;
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

	private final int TIE = 0;
	private final int P1_WIN = 1;
	private final int P2_WIN = 2;
	
	
	private PhysicsWorld mPhysicsWorld;

	private boolean paused = false;

	private Sheep sheepP1;
	private Sheep sheepP2;
	private Sprite arena;
	
	private Entity nailP1;
	private Entity nailP2;
	
	private Sprite pauseButton;
	private MenuScene menuPauseScene;
	private MenuScene menuRoundOverScene;
	private Text winningAnnouncementText;
	

	private GestureDetector gDetectorP1;
	private GestureDetector gDetectorP2;
	// private ScrollMenu gamePlayHUD;
	
	
	
	
	@Override
	public void createScene() {
		Debug.d("Player 1: " + gameDataManager.p1SheepIndex);
		Debug.d("Player 2: " + gameDataManager.p2SheepIndex);
		createBackground();
		createPauseButton();
		createMenuPauseScene();
		createMenuRoundOverScene();
		

		setTouchAreaBindingOnActionDownEnabled(true);

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		registerUpdateHandler(this.mPhysicsWorld);
		
		createWall();
		createArena();
		createSheep();
		createNail();
		
		
		registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				if(sheepP1.isOut() && sheepP2.isOut()){
					matchOver(TIE);
				}
				else if(sheepP1.isOut()){
					matchOver(P2_WIN);
				}
				else if(sheepP2.isOut()){
					matchOver(P1_WIN);
				}
				pTimerHandler.reset();
			}                      
		}));

		
		
		
		
		
		
		

		/* We allow only the server to actively send around messages. */
//		if(activity.mSocketServer != null) {
//			this.setOnSceneTouchListener(new IOnSceneTouchListener() {
//				@Override
//				public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
//					if(pSceneTouchEvent.isActionDown()) {
//						try {
//							final MoveSheepServerMessage moveSheepServerMessage = (MoveSheepServerMessage) activity.mMessagePool.obtainMessage(MainActivity.FLAG_MESSAGE_SERVER_MOVE_SHEEP);
//							
//							// moveSheepServerMessage.set(activity.mFaceIDCounter++, pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
//
//							activity.mSocketServer.sendBroadcastServerMessage(moveSheepServerMessage);
//
//							// activity.mMessagePool.recycleMessage(moveSheepServerMessage);
//						} catch (final IOException e) {
//							Debug.e(e);
//						}
//						return true;
//					} else {
//						return true;
//					}
//				}
//			});
//
//			this.setTouchAreaBindingOnActionDownEnabled(true);
//		}
		
		
		
		
	}

	protected void matchOver(int winner) {
		switch (winner) {
		case TIE:
			gameDataManager.winner = 0;
			break;
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
	}

	private void createNail() {
		nailP1 = new Entity();
		nailP2 = new Entity();
		
		for (int i = 0; i < resourcesManager.nailNormalRegions.size(); i++) {
			final int _i = i;
			TiledSprite nail_part_1 = new TiledSprite(80, 240 - ((i - 2) * 20), resourcesManager.nailNormalRegions.get(i), vbom){
				@Override	
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					
					sheepP1.moveForward(2 - _i);
					resourcesManager.vibrator.vibrate(50);
					gDetectorP1.onTouchEvent(pSceneTouchEvent.getMotionEvent());
					
					if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
						
						for (int j = 0; j < nailP1.getChildCount(); j++) {
							((TiledSprite) nailP1.getChildByIndex(j)).setCurrentTileIndex(0);
						}
						
						this.setCurrentTileIndex(1);
					} else {
						this.setCurrentTileIndex(0);
					}
					
					return false;
				}
			};
			
			nailP1.attachChild(nail_part_1);
			registerTouchArea(nail_part_1);
			

			TiledSprite nail_part_2 = new TiledSprite(720, 240 - ((i - 2) * 20), resourcesManager.nailNormalRegions.get(i), vbom){
				@Override	
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					sheepP2.moveForward(2 - _i);
					resourcesManager.vibrator.vibrate(50);
					gDetectorP2.onTouchEvent(pSceneTouchEvent.getMotionEvent());
					
					if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
						
						for (int j = 0; j < nailP2.getChildCount(); j++) {
							((TiledSprite) nailP2.getChildByIndex(j)).setCurrentTileIndex(0);
						}
						
						this.setCurrentTileIndex(1);
					} else {
						this.setCurrentTileIndex(0);
					}
					
					return false;
				}
			};
			
			nailP2.attachChild(nail_part_2);
			registerTouchArea(nail_part_2);
			
			FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
			Body bodyNailP1 = PhysicsFactory.createCircleBody(mPhysicsWorld, nail_part_1, BodyType.StaticBody, FIXTURE_DEF);
			Body bodyNailP2 = PhysicsFactory.createCircleBody(mPhysicsWorld, nail_part_2, BodyType.StaticBody, FIXTURE_DEF);
			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nail_part_1, bodyNailP1, true, true));
			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nail_part_2, bodyNailP2, true, true));
		}
		
		createNewThread();
		
		attachChild(nailP1);
		attachChild(nailP2);
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

	private void createWall() {
		final Rectangle ground = new Rectangle(camera.getWidth()/2, camera.getHeight() - 2, camera.getWidth(), 2, vbom);
		final Rectangle roof = new Rectangle(camera.getWidth()/2, 0, camera.getWidth(), 2, vbom);
		final Rectangle left = new Rectangle(0, camera.getHeight()/2, 2, camera.getHeight(), vbom);
		final Rectangle right = new Rectangle(camera.getWidth() - 2, camera.getHeight()/2, 2, camera.getHeight(), vbom);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);
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
		menuRoundOverScene = new MenuScene(camera);
		menuRoundOverScene.setPosition(CAMERA_WIDTH/2, CAMERA_HEIGHT/2);

		final Sprite overlay = new Sprite(0, 0, resourcesManager.gamePlayOverlayRegion, vbom);
	    final IMenuItem nextItem = new ScaleMenuItemDecorator(new SpriteMenuItem(NEXT, resourcesManager.gamePlayNextRegion, vbom), 1.2f, 1);
	    final IMenuItem nextRoundItem = new ScaleMenuItemDecorator(new SpriteMenuItem(NEXT_ROUND, resourcesManager.gamePlayNextRoundRegion, vbom), 1.2f, 1);
	    
	    menuRoundOverScene.attachChild(overlay);
	    menuRoundOverScene.addMenuItem(nextItem);
	    menuRoundOverScene.addMenuItem(nextRoundItem);
	    
	    menuRoundOverScene.buildAnimations();
	    menuRoundOverScene.setBackgroundEnabled(false);

	    nextItem.setPosition(0, -80);
	    nextRoundItem.setPosition(0, -160);
	    

		winningAnnouncementText = new Text(0, 80, resourcesManager.font, "Player " + gameDataManager.winner + " WON", vbom);
		menuRoundOverScene.attachChild(winningAnnouncementText);
		
		menuRoundOverScene.setOnMenuItemClickListener(this);
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
			playNextRound();
			return true;
		default:
			return false;
		}
	}

	private void showMenuRoundOverScene() {
		if(!isPaused()){
			engine.getScene().setIgnoreUpdate(true);
		    setChildScene(menuRoundOverScene, false, true, true);
		    pauseButton.setVisible(false);
		    setPaused(true);
		    winningAnnouncementText.setText("Player " + gameDataManager.winner + " WON");
		}
	}
	
	private void playNextRound() {
		if(isPaused()){
		    clearChildScene();
			engine.getScene().setIgnoreUpdate(false);
		    pauseButton.setVisible(true);
		    setPaused(false);
		}
		SceneManager.getInstance().reloadGamePlay(engine);
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
