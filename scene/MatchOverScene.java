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
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class MatchOverScene extends BaseScene implements IOnMenuItemClickListener {

	private final int REMATCH = 0;
	private final int CHANGE_SHEEP = 1;
	private final int BACK_TO_MENU= 2;
	private final int EXIT = 3;
	
	private MenuScene menuChildScene;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	private void createBackground() {
		attachChild(new Sprite(400,  240, resourcesManager.matchOverBackgroundRegion, vbom)
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

	    final IMenuItem rematchItem = new ScaleMenuItemDecorator(new SpriteMenuItem(REMATCH, resourcesManager.matchOverRematchRegion, vbom), 1.2f, 1);
	    final IMenuItem changeSheepItem = new ScaleMenuItemDecorator(new SpriteMenuItem(CHANGE_SHEEP, resourcesManager.matchOverChangeSheepRegion, vbom), 1.2f, 1);
	    final IMenuItem backToMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(BACK_TO_MENU, resourcesManager.matchOverBackToMenuRegion, vbom), 1.2f, 1);
	    // final IMenuItem exitItem = new ScaleMenuItemDecorator(new SpriteMenuItem(EXIT, resourcesManager.matchOverExitRegion, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(rematchItem);
	    menuChildScene.addMenuItem(changeSheepItem);
	    menuChildScene.addMenuItem(backToMenuItem);
	    // menuChildScene.addMenuItem(exitItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    rematchItem.setPosition(0, 0);
	    changeSheepItem.setPosition(0, -80);
	    backToMenuItem.setPosition(0, -160);
	    // exitItem.setPosition(0, -160);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuSceneFromMatchOver(engine);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case REMATCH:
			SceneManager.getInstance().loadGamePlaySceneFromMatchOver(engine);
			return true;
		case CHANGE_SHEEP:
			SceneManager.getInstance().loadSheepSelectionSceneFromMatchOver(engine);
			return true;
		case BACK_TO_MENU:
			SceneManager.getInstance().loadMenuSceneFromMatchOver(engine);
			return true;
		case EXIT:
			return true;
		default:
			return false;
		}
	}

}
