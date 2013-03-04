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
import com.ralibi.dodombaan.component.ScrollEntity;
import com.ralibi.dodombaan.component.ScrollPanel;
import com.ralibi.dodombaan.manager.GameDataManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class SheepSelectionScene extends BaseScene implements IOnMenuItemClickListener {


	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	private final int MENU_BACK = 0;
	private final int MENU_NEXT = 1;
	
	private MenuScene menuChildScene;

	private ScrollEntity scrollEntityP1;
	private ScrollEntity scrollEntityP2;

	
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		createScrollEntity();
	}

	private void createScrollEntity() {

		ScrollPanel sheepSelectP1 = new ScrollPanel();
		scrollEntityP1 = new ScrollEntity(200, 240, 250, 200, sheepSelectP1);
		scrollEntityP1.buildSprite(200, 200, resourcesManager.sheepSelectionSheepRegions, vbom);
		scrollEntityP1.attachChild(sheepSelectP1);
		registerTouchArea(scrollEntityP1);
		attachChild(scrollEntityP1);
		

		ScrollPanel sheepSelectP2 = new ScrollPanel();
		scrollEntityP2 = new ScrollEntity(600, 240, 250, 200, sheepSelectP2);
		scrollEntityP2.buildSprite(200, 200, resourcesManager.sheepSelectionSheepRegions, vbom);
		scrollEntityP2.attachChild(sheepSelectP2);
		registerTouchArea(scrollEntityP2);
		attachChild(scrollEntityP2);
	}

	protected void itemClick(int i) {
		Debug.d("Clicking: " + i);
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
	
	
	
	public void untouchScrollEntities() {
		if(this.scrollEntityP1 != null && this.scrollEntityP2 != null){
			this.scrollEntityP1.getScrollPanel().setTouching(false);
			this.scrollEntityP2.getScrollPanel().setTouching(false);
		}
	}
	

	@Override
	public void onBackKeyPressed() {
	    SceneManager.getInstance().loadMenuSceneFromSheepSelection(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SHEEP_SELECTION;
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
			GameDataManager.getInstance().p1SheepIndex = scrollEntityP1.getSelectedMenuIndex();
			GameDataManager.getInstance().p2SheepIndex = scrollEntityP2.getSelectedMenuIndex();
			SceneManager.getInstance().loadMatchSettingsSceneFromSheepSelection(engine);
			return true;
		default:
			return false;
		}
	}
	

}
