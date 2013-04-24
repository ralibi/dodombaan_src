package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;


import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
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

	
	ButtonSprite nextButton;
	
	
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		createScrollMenuSheep();
	}

	private void createScrollMenuSheep() {
		scrollMenuSheepP1 = new ScrollMenuEntity(200, 300, 250, 300, new DeselectListener() {
			@Override
			public void onSelect() {
				if(scrollMenuSheepP2.getSelectedMenuIndex() >= 0){
					nextButton.setEnabled(true);
				}
				resourcesManager.majorSound.play();
			}
			
			@Override
			public void onDeselect() {
				nextButton.setEnabled(false);
			}
		});
		scrollMenuSheepP1.buildSprite(100, 200, 200, 200, resourcesManager.sheepSelectionSheepRegions, this, vbom);
		registerTouchArea(scrollMenuSheepP1);
		registerTouchArea(scrollMenuSheepP1.getScrollPanel().getChildByIndex(0));
		attachChild(scrollMenuSheepP1);
		// attachChild(scrollMenuSheepP1.getSelectButton());
		
		buildSheepCharacteristicMenu(scrollMenuSheepP1);
		
		

		scrollMenuSheepP2 = new ScrollMenuEntity(600, 300, 250, 300, new DeselectListener() {
			@Override
			public void onSelect() {
				if(scrollMenuSheepP1.getSelectedMenuIndex() >= 0){
					nextButton.setEnabled(true);
				}
				resourcesManager.minorSound.play();
			}
			
			@Override
			public void onDeselect() {
				nextButton.setEnabled(false);
			}
		});
		scrollMenuSheepP2.buildSprite(100, 200, 200, 200, resourcesManager.sheepSelectionSheepRegions, this, vbom);
		registerTouchArea(scrollMenuSheepP2);
		registerTouchArea(scrollMenuSheepP2.getScrollPanel().getChildByIndex(0));
		attachChild(scrollMenuSheepP2);

		buildSheepCharacteristicMenu(scrollMenuSheepP2);
	}

	private void buildSheepCharacteristicMenu(ScrollMenuEntity scrollMenuSheep) {
		// TODO Auto-generated method stub
		
		
		for (int i = 0; i < scrollMenuSheep.getScrollPanel().getItemCount(); i++) {

			final Entity characteristicGroup = new Entity(0, 0);
			int anchorX = 10 + i * 200;

			// Strength
			final Entity strengthGroup = new Entity(0, 0);
			Text strenghtText = new Text(0, 88, resourcesManager.fontSmall, "Strength", vbom);
			strenghtText.setPosition(anchorX + strenghtText.getWidth()/2f, 192);
			strengthGroup.attachChild(strenghtText);
			strengthGroup.attachChild(getCharacteristicMeter(anchorX, 168, GameConfigurationManager.STRENGTH[i]));
			characteristicGroup.attachChild(strengthGroup);

			
			// Speed
			final Entity speedGroup = new Entity(0, 0);
			Text speedText = new Text(0, 88, resourcesManager.fontSmall, "Speed", vbom);
			speedText.setPosition(anchorX + speedText.getWidth()/2f, 128);
			speedGroup.attachChild(speedText);
			speedGroup.attachChild(getCharacteristicMeter(anchorX, 104, GameConfigurationManager.SPEED[i]));
			characteristicGroup.attachChild(speedGroup);

			final Entity agilityGroup = new Entity(0, 0);
			Text agilityText = new Text(0, 88, resourcesManager.fontSmall, "Agility", vbom);
			agilityText.setPosition(anchorX + agilityText.getWidth()/2f, 64);
			agilityGroup.attachChild(agilityText);
			agilityGroup.attachChild(getCharacteristicMeter(anchorX, 44, GameConfigurationManager.AGILITY[i]));
			characteristicGroup.attachChild(agilityGroup);
			
			scrollMenuSheep.getScrollPanel().attachChild(characteristicGroup);
		}
	}
	
	private Entity getCharacteristicMeter(int posX, int posY, int nRect){

		/* Create the rectangles. */
		final Entity strengthRectangleGroup = new Entity(0, 0);
		for (int i = 0; i < nRect; i++) {
			final Rectangle rect = this.makeColoredRectangle(posX + 16 + i * 30, posY, .5f, .2f, .1f);
			strengthRectangleGroup.attachChild(rect);
		}
		return strengthRectangleGroup;
	}
	

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, 24, 24, vbom);
		coloredRect.setColor(pRed, pGreen, pBlue);
		return coloredRect;
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
	    nextButton = new ButtonSprite(600, 40, resourcesManager.nextNormalRegion, resourcesManager.nextPressedRegion, resourcesManager.nextDisabledRegion, vbom, new OnClickListener() {	
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,	float pTouchAreaLocalY) {
				gameDataManager.p1SheepIndex = scrollMenuSheepP1.getSelectedMenuIndex();
				gameDataManager.p2SheepIndex = scrollMenuSheepP2.getSelectedMenuIndex();
				SceneManager.getInstance().loadMatchSettingsSceneFromSheepSelection(engine);
			}
		});
	    
	    registerTouchArea(nextButton);
	    attachChild(nextButton);
	    
	    menuChildScene.addMenuItem(backItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);

	    backItem.setPosition(-200, -200);
	    
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
		scrollMenuSheepP1.detachChildren();
		scrollMenuSheepP2.detachChildren();
		detachChild(scrollMenuSheepP1);
		detachChild(scrollMenuSheepP2);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_BACK:
			SceneManager.getInstance().loadMenuSceneFromSheepSelection(engine);
			return true;
		case MENU_NEXT:
			gameDataManager.p1SheepIndex = scrollMenuSheepP1.getSelectedMenuIndex();
			gameDataManager.p2SheepIndex = scrollMenuSheepP2.getSelectedMenuIndex();
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
