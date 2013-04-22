package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.ScrollPanel;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MatchSettingsScene extends BaseScene implements IOnMenuItemClickListener {

	private final int MENU_BACK = 0;
	private final int MENU_NEXT = 1;
	
	private MenuScene menuChildScene;

	private ScrollMenuEntity scrollEntityArena;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		createArenaScrollMenu();
	}

	private void createArenaScrollMenu() {
		ScrollPanel arenaPanel = new ScrollPanel();
		scrollEntityArena = new ScrollMenuEntity(400, 240, 720, 240, arenaPanel);
		scrollEntityArena.buildSprite(300, 120, 600, 240, resourcesManager.matchSettingsArenaRegions, this, vbom);
		scrollEntityArena.attachChild(arenaPanel);
		registerTouchArea(scrollEntityArena);
		attachChild(scrollEntityArena);
	}

	private void createBackground() {
		attachChild(new Sprite(400,  240, resourcesManager.matchSettingsBackgroundRegion, vbom)
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

	    final IMenuItem backItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BACK, resourcesManager.matchSettingsBackRegion, vbom), 1.2f, 1);
	    final IMenuItem nextItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_NEXT, resourcesManager.matchSettingsNextRegion, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(backItem);
	    menuChildScene.addMenuItem(nextItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    backItem.setPosition(-200, -200);
	    nextItem.setPosition(200, -200);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadSheepSelectionSceneFromMatchSettings(engine);
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
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_BACK:
			SceneManager.getInstance().loadSheepSelectionSceneFromMatchSettings(engine);
			return true;
		case MENU_NEXT:
			gameDataManager.arenaIndex = scrollEntityArena.getSelectedMenuIndex();
			SceneManager.getInstance().loadGamePlayScene(engine);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void unTouchScrollMenu() {
		this.scrollEntityArena.getScrollPanel().setTouching(false);
	}

}
