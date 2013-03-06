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

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	//---------------------------------------------
    // CONSTANT
    //---------------------------------------------

	private final int MENU_MULTIPLAYER_SINGLE_DEVICE = 0;
	private final int MENU_MULTIPLAYER_OVER_BLUETOOTH = 1;
	private final int MENU_SETTINGS = 2;
	private final int MENU_EXIT = 3;
	
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	private MenuScene menuChildScene;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();	
	}

	private void createBackground() {
		attachChild(new Sprite(400,  240, resourcesManager.menuBackgroundRegion, vbom)
		{
			@Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		});
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(400, 240);

	    final IMenuItem multiplayerSingleDeviceItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_MULTIPLAYER_SINGLE_DEVICE, resourcesManager.multiplayerSingleDeviceRegion, vbom), 1.2f, 1);
	    final IMenuItem multiplayerOverBluetoothItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_MULTIPLAYER_OVER_BLUETOOTH, resourcesManager.multiplayerOverBluetoothRegion, vbom), 1.2f, 1);
	    final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SETTINGS, resourcesManager.settingsRegion, vbom), 1.2f, 1);
	    final IMenuItem exitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EXIT, resourcesManager.exitRegion, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(multiplayerSingleDeviceItem);
	    menuChildScene.addMenuItem(multiplayerOverBluetoothItem);
	    menuChildScene.addMenuItem(settingsMenuItem);
	    menuChildScene.addMenuItem(exitMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    multiplayerSingleDeviceItem.setPosition(0, 80);
	    multiplayerOverBluetoothItem.setPosition(0, 0);
	    settingsMenuItem.setPosition(0, -80);
	    exitMenuItem.setPosition(0, -160);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_MULTIPLAYER_SINGLE_DEVICE:
			SceneManager.getInstance().loadSheepSelectionSceneFromMenu(engine);
			return true;
		case MENU_MULTIPLAYER_OVER_BLUETOOTH:
			return true;
		case MENU_SETTINGS:
			SceneManager.getInstance().loadSettingsScene(engine);
			return true;
		case MENU_EXIT:
			System.exit(0);
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
