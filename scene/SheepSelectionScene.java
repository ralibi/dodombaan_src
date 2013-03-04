package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ClippingEntity;
import com.ralibi.dodombaan.component.ScrollEntity;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class SheepSelectionScene extends BaseScene implements IOnMenuItemClickListener {

	private final int MENU_BACK = 0;
	private final int MENU_NEXT = 1;
	
	private MenuScene menuChildScene;

	private final int originX = 0;
	private final int originY = 100;
	private final int sheepWidth = 200;
	private final int sheepHeight = 200;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();

		
		Sprite sheep1 = new Sprite(originX + 0 * sheepWidth, originY, resourcesManager.sheepSelectionSheep1Region, vbom);
		Sprite sheep2 = new Sprite(originX + 1 * sheepWidth, originY, resourcesManager.sheepSelectionSheep2Region, vbom);
		Sprite sheep3 = new Sprite(originX + 2 * sheepWidth, originY, resourcesManager.sheepSelectionSheep3Region, vbom);
		Sprite sheep4 = new Sprite(originX + 3 * sheepWidth, originY, resourcesManager.sheepSelectionSheep4Region, vbom);
		Sprite sheep5 = new Sprite(originX + 4 * sheepWidth, originY, resourcesManager.sheepSelectionSheep5Region, vbom);
		Sprite sheep6 = new Sprite(originX + 5 * sheepWidth, originY, resourcesManager.sheepSelectionSheep6Region, vbom);
		
		ScrollEntity container = new ScrollEntity(400, 200, 200, 200);
		Entity sheepSelect = new Entity();
		sheepSelect.attachChild(sheep1);
		sheepSelect.attachChild(sheep2);
		sheepSelect.attachChild(sheep3);
		sheepSelect.attachChild(sheep4);
		sheepSelect.attachChild(sheep5);
		sheepSelect.attachChild(sheep6);
		container.attachChild(sheepSelect);
		registerTouchArea(container);
		attachChild(container);
	}

	private void createBackground() {
		attachChild(new Sprite(400,  240, resourcesManager.sheepSelectionBackgroundRegion, vbom)
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

	    final IMenuItem backItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BACK, resourcesManager.sheepSelectionBackRegion, vbom), 1.2f, 1);
	    final IMenuItem nextItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_NEXT, resourcesManager.sheepSelectionNextRegion, vbom), 1.2f, 1);
	    
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
	    SceneManager.getInstance().loadMenuSceneFromSheepSelection(engine);
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
		case MENU_BACK:
			SceneManager.getInstance().loadMenuSceneFromSheepSelection(engine);
			return true;
		case MENU_NEXT:
			SceneManager.getInstance().loadMatchSettingsSceneFromSheepSelection(engine);
			return true;
		default:
			return false;
		}
	}

}
