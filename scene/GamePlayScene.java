package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;
import com.ralibi.dodombaan.object.Sheep;

public class GamePlayScene extends BaseScene implements IOnMenuItemClickListener, IAccelerationListener {

	private final int PAUSE = 0;
	private final int RESUME = 1;
	private final int EXIT_TO_MENU= 2;
	private final int P1_WIN = 3;
	private final int P2_WIN = 4;
	
	
	private PhysicsWorld mPhysicsWorld;

	private boolean paused = false;

	private Sheep sheepP1;
	private Sheep sheepP2;
	private Sprite arena;
	private Sprite nailP1;
	private Sprite nailP2;
	
	private Sprite pauseButton;
	private MenuScene menuChildScene;
	
	
	// private ScrollMenu gamePlayHUD;
	
	
	
	
	@Override
	public void createScene() {
		Debug.d("Player 1: " + gameDataManager.p1SheepIndex);
		Debug.d("Player 2: " + gameDataManager.p2SheepIndex);
		createBackground();
		createPauseButton();
		createMenuChildScene();
		

		setTouchAreaBindingOnActionDownEnabled(true);

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		registerUpdateHandler(this.mPhysicsWorld);
		
		createWall();
		createArena();
		createSheep();
		createNail();
		
		engine.enableAccelerationSensor(activity, this);
	}

	private void createArena() {
		arena = new Sprite(400, 240, resourcesManager.gamePlayPlayingArenaRegions.get(gameDataManager.arenaIndex), vbom);
		attachChild(arena);
	}

	private void createNail() {
		nailP1 = new Sprite(80, 240, resourcesManager.gamePlayNailRegion, vbom){
			@Override	
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				sheepP1.moveForward();
				return false;
			}
		};
		nailP2 = new Sprite(720, 240, resourcesManager.gamePlayNailRegion, vbom){
			@Override	
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				sheepP2.moveForward();
				return false;
			}
		};
		FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		Body bodyNailP1 = PhysicsFactory.createCircleBody(mPhysicsWorld, nailP1, BodyType.StaticBody, FIXTURE_DEF);
		Body bodyNailP2 = PhysicsFactory.createCircleBody(mPhysicsWorld, nailP2, BodyType.StaticBody, FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nailP1, bodyNailP1, true, true));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nailP2, bodyNailP2, true, true));

		registerTouchArea(nailP1);
		registerTouchArea(nailP2);
		
		attachChild(nailP1);
		attachChild(nailP2);
	}

	private void createSheep() {
		
//		registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
//			@Override
//			public void onTimePassed(final TimerHandler pTimerHandler) {
//				if (bodyAbdomain != null) {
//					
//					applyCenterForce(bodyAbdomain);
//					applyCenterForce(bodyHorn);
//					applyCenterForce(bodyTail);
//				}              
//				//Reset the timer
//				pTimerHandler.reset();
//			}                      
//		}));


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


	protected void applyCenterForce(Body body) {
		// float bodyForce = 100 * (240 - (body.getPosition().y * 32));
//		if(body.getPosition().y > 240f/32){
//			bodyForce = -bodyForce;
//		}
		// body.applyForce(new Vector2(0, bodyForce), body.getPosition());
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
	
	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(400, 240);

	    // final IMenuItem pauseItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PAUSE, resourcesManager.gamePlayPauseRegion, vbom), 1.2f, 1);
	    final IMenuItem resumeItem = new ScaleMenuItemDecorator(new SpriteMenuItem(RESUME, resourcesManager.gamePlayResumeRegion, vbom), 1.2f, 1);
	    final IMenuItem exitToMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(EXIT_TO_MENU, resourcesManager.gamePlayExitToMenuRegion, vbom), 1.2f, 1);
	    
	    final IMenuItem p1WinItem = new ScaleMenuItemDecorator(new SpriteMenuItem(P1_WIN, resourcesManager.gamePlayP1WinRegion, vbom), 1.2f, 1);
	    final IMenuItem p2WinItem = new ScaleMenuItemDecorator(new SpriteMenuItem(P2_WIN, resourcesManager.gamePlayP2WinRegion, vbom), 1.2f, 1);
	    
	    // menuChildScene.addMenuItem(pauseItem);
	    final Sprite overlay = new Sprite(0, 0, resourcesManager.gamePlayOverlayRegion, vbom);
	    
	    menuChildScene.attachChild(overlay);
	    menuChildScene.addMenuItem(resumeItem);
	    menuChildScene.addMenuItem(exitToMenuItem);
	    
	    menuChildScene.addMenuItem(p1WinItem);
	    menuChildScene.addMenuItem(p2WinItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    // pauseItem.setPosition(0, 0);
	    resumeItem.setPosition(0, -80);
	    exitToMenuItem.setPosition(0, -160);
	    p1WinItem.setPosition(-200, -200);
	    p2WinItem.setPosition(200, -200);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
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
		case P1_WIN:
			SceneManager.getInstance().loadMatchOverScene(engine);
			return true;
		case P2_WIN:
			SceneManager.getInstance().loadMatchOverScene(engine);
			return true;
		default:
			return false;
		}
	}

	protected void pauseGame() {
		if(!isPaused()){
			engine.getScene().setIgnoreUpdate(true);
		    setChildScene(menuChildScene, false, true, true);
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

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
//		final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
//		this.mPhysicsWorld.setGravity(gravity);
//		Vector2Pool.recycle(gravity);
	}
//
//	
//	public void createEllipse(){
//		double a = 2.0, b = 1.0;
//		int n = 4;
//		double[] x = new double[n];
//		double[] y = new double[n];
//		x[0] = 0.0; y[0] = b;
//		x[n-1] = a; y[n-1] = 0.0;
//	
//		double xratio;
//		int j;
//		for (j = 1; j <= n-2; j++)
//		{
//			x[j] = (double)j/(double)(n-1);
//			xratio = x[j]/a;
//			y[j] = b*Math.sqrt(1.0 - xratio*xratio);
//		}
//	
//		int imax = 1024;
//		for (int i = 0; i < imax; i++)
//		{
//			for (j = 1; j <= n-2; j++)
//			{
//				double xdiff = x[j+1] - x[j-1];
//				double ydiff = y[j+1] - y[j-1];
//				double xavr = 0.5*(x[j+1] + x[j-1]);
//				double yavr = 0.5*(y[j+1] + y[j-1]);
//				x[j] = xavr - (ydiff/xdiff)*(y[j] - yavr);
//				xratio = x[j]/a;
//				y[j] = b*Math.sqrt(1.0 - xratio*xratio);
//			}
//		}
//	
//		double maxLenDiff = 0.0;
//		double dx = x[1] - x[0];
//		double dy = y[1] - y[0];
//		double length0 = Math.sqrt(dx*dx + dy*dy);
//		for (j = 2; j <= n-1; j++)
//		{
//			dx = x[j] - x[j-1];
//			dy = y[j] - y[j-1];
//			double length1 = Math.sqrt(dx*dx + dy*dy);
//			double lenDiff = Math.abs(length1 - length0);
//			if (lenDiff > maxLenDiff)
//			{
//				maxLenDiff = lenDiff;
//			}
//			length0 = length1;
//		}
//	
//		// maxLenDiff is nearly zero (about 1e-16)
//	
//	}
}
