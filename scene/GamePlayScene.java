package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.GameDataManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class GamePlayScene extends BaseScene implements IOnMenuItemClickListener {

	private final int PAUSE = 0;
	private final int RESUME = 1;
	private final int EXIT_TO_MENU= 2;
	private final int P1_WIN = 3;
	private final int P2_WIN = 4;
	
	//private ScrollMenu gamePlayHUD;
	
	private MenuScene menuChildScene;
	
	@Override
	public void createScene() {
		Debug.d("Player 1: " + GameDataManager.getInstance().p1SheepIndex);
		Debug.d("Player 2: " + GameDataManager.getInstance().p2SheepIndex);
		createBackground();
		createMenuChildScene();
		createHUD();
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

	    final IMenuItem pauseItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PAUSE, resourcesManager.gamePlayPauseRegion, vbom), 1.2f, 1);
	    final IMenuItem resumeItem = new ScaleMenuItemDecorator(new SpriteMenuItem(RESUME, resourcesManager.gamePlayResumeRegion, vbom), 1.2f, 1);
	    final IMenuItem exitToMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(EXIT_TO_MENU, resourcesManager.gamePlayExitToMenuRegion, vbom), 1.2f, 1);
	    
	    final IMenuItem p1WinItem = new ScaleMenuItemDecorator(new SpriteMenuItem(P1_WIN, resourcesManager.gamePlayP1WinRegion, vbom), 1.2f, 1);
	    final IMenuItem p2WinItem = new ScaleMenuItemDecorator(new SpriteMenuItem(P2_WIN, resourcesManager.gamePlayP2WinRegion, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(pauseItem);
	    menuChildScene.addMenuItem(resumeItem);
	    menuChildScene.addMenuItem(exitToMenuItem);
	    
	    menuChildScene.addMenuItem(p1WinItem);
	    menuChildScene.addMenuItem(p2WinItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    pauseItem.setPosition(0, 0);
	    resumeItem.setPosition(0, -80);
	    exitToMenuItem.setPosition(0, -160);
	    p1WinItem.setPosition(-200, -200);
	    p2WinItem.setPosition(200, -200);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}
	
	private void createHUD()
	{
//		Sprite hex = new Sprite(100, 300, resourcesManager.gamePlayExitToMenuRegion, vbom);
//	    gamePlayHUD = new ScrollMenu(0, 0, camera, vbom);
//	    gamePlayHUD.attachChild(hex);  
//	    gamePlayHUD.registerTouchArea(hex);
//	    camera.setHUD(gamePlayHUD);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void unTouchScrollMenu() {
		// TODO Auto-generated method stub
		
	}

}
