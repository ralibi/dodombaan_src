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
import com.ralibi.dodombaan.component.ScrollMenuEntity;
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

	private ScrollMenuEntity scrollMenuSheepP1;
	private ScrollMenuEntity scrollMenuSheepP2;

	
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		createScrollMenuSheep();
	}

	private void createScrollMenuSheep() {

		ScrollPanel sheepSelectP1 = new ScrollPanel();
		scrollMenuSheepP1 = new ScrollMenuEntity(200, 240, 250, 200, sheepSelectP1);
		scrollMenuSheepP1.buildSprite(200, 200, resourcesManager.sheepSelectionSheepRegions, vbom);
		scrollMenuSheepP1.attachChild(sheepSelectP1);
		registerTouchArea(scrollMenuSheepP1);
		attachChild(scrollMenuSheepP1);
		

		ScrollPanel sheepSelectP2 = new ScrollPanel();
		scrollMenuSheepP2 = new ScrollMenuEntity(600, 240, 250, 200, sheepSelectP2);
		scrollMenuSheepP2.buildSprite(200, 200, resourcesManager.sheepSelectionSheepRegions, vbom);
		scrollMenuSheepP2.attachChild(sheepSelectP2);
		registerTouchArea(scrollMenuSheepP2);
		attachChild(scrollMenuSheepP2);
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
			GameDataManager.getInstance().p1SheepIndex = scrollMenuSheepP1.getSelectedMenuIndex();
			GameDataManager.getInstance().p2SheepIndex = scrollMenuSheepP2.getSelectedMenuIndex();
			SceneManager.getInstance().loadMatchSettingsSceneFromSheepSelection(engine);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void unTouchScrollMenu() {
		this.scrollMenuSheepP1.getScrollPanel().setTouching(false);
		this.scrollMenuSheepP2.getScrollPanel().setTouching(false);
	}
	

}
