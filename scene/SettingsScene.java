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

public class SettingsScene extends BaseScene implements IOnMenuItemClickListener {

	private final int RESET = 0;
	private final int OK = 1;
	
	private MenuScene menuChildScene;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	private void createBackground() {
		attachChild(new Sprite(400,  240, resourcesManager.settingsBackgroundRegion, vbom)
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

	    final IMenuItem resetItem = new ScaleMenuItemDecorator(new SpriteMenuItem(RESET, resourcesManager.settingsResetRegion, vbom), 1.2f, 1);
	    final IMenuItem okItem = new ScaleMenuItemDecorator(new SpriteMenuItem(OK, resourcesManager.settingsOkRegion, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(resetItem);
	    menuChildScene.addMenuItem(okItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    resetItem.setPosition(0, 0);
	    okItem.setPosition(0, -80);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuSceneFromSettings(engine);
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
		case RESET:
			// SceneManager.getInstance().loadGamePlaySceneFromMatchOver(engine);
			return true;
		case OK:
			SceneManager.getInstance().loadMenuSceneFromSettings(engine);
			return true;
		default:
			return false;
		}
	}

}
