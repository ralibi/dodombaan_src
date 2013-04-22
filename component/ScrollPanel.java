package com.ralibi.dodombaan.component;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.debug.Debug;


public class ScrollPanel extends Entity implements IOnMenuItemClickListener {

	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	private PhysicsHandler mPhysicsHandler;
	private boolean movingRight = false;
	private boolean touching = false;

	private int itemCount = 0;
	private int itemWidth = 0;
	private int itemHeight = 0;
	private int selectedIndex = 0;

	//---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

	public ScrollPanel() {
		super();
		
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
		this.mPhysicsHandler.setVelocity(10, 0);
	}
	
	
	
	//---------------------------------------------
    // GETTERS SETTERS
    //---------------------------------------------
	
	public PhysicsHandler getmPhysicsHandler() {
		return mPhysicsHandler;
	}

	public void setmPhysicsHandler(PhysicsHandler mPhysicsHandler) {
		this.mPhysicsHandler = mPhysicsHandler;
	}

	
	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isTouching() {
		return touching;
	}

	public void setTouching(boolean touching) {
		this.touching = touching;
	}
	
	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getItemWidth() {
		return itemWidth;
	}

	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}

	public int getItemHeight() {
		return itemHeight;
	}

	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	public void navigate(int direction){
		switch (direction) {
		case -1:
			
			break;

		case 1:
			
			break;
		default:
			break;
		}
	}


	//---------------------------------------------
    // INSTANCE METHODS
    //---------------------------------------------
	
	public void enableItemTouch() {
		for (int i = 0; i < getChildCount(); i++) {
		    // ((Sprite) getChildByIndex(i)).onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY)
		}
	}
	

	//---------------------------------------------
    // OVERRIDES
    //---------------------------------------------
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		// this.setX(this.getX() + 1);
		super.onManagedUpdate(pSecondsElapsed);
		
		if(!this.touching){
			float paddingLeft = (getParent().getWidth()-itemWidth)/2;
			
			float posMinX = paddingLeft;
			float posMaxX = ((1-itemCount) * itemWidth) + paddingLeft;
			float posSnapX = (-selectedIndex * itemWidth) + paddingLeft;
			

			if(this.mPhysicsHandler.getVelocityX() == 0){
				if(this.getX() > posSnapX){
					if(this.getX() - posSnapX > itemWidth/2 && selectedIndex > 0){
						setMovingRight(true);
						this.mPhysicsHandler.setVelocityX(itemWidth * 2);
						selectedIndex -= 1;
					}
					else{
						setMovingRight(false);
						this.mPhysicsHandler.setVelocityX(-itemWidth * 2);
					}
					
				}else if(this.getX() < posSnapX){
					if(posSnapX - this.getX() > itemWidth/2 && selectedIndex < itemCount - 1){
						setMovingRight(false);
						this.mPhysicsHandler.setVelocityX(-itemWidth * 2);
						selectedIndex += 1;
					}
					else{
						setMovingRight(true);
						this.mPhysicsHandler.setVelocityX(itemWidth * 2);
					}
				}
			}
			else{
				if(movingRight){
					if(this.getX() > posSnapX){
						this.setX(posSnapX);
					}
				}else{
					if(this.getX() < posSnapX){
						this.setX(posSnapX);
					}
				}
			}
			
			if(this.getX() > posMinX){
				this.setX(posMinX);
				selectedIndex = 0;
			}
			else if(this.getX() < posMaxX){
				this.setX(posMaxX);
				selectedIndex = itemCount - 1;
			}
			
		}
	}



	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}
}
