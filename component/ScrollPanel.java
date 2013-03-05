package com.ralibi.dodombaan.component;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;


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
			
			if(movingRight && this.mPhysicsHandler.getVelocityX() < 0){
				this.mPhysicsHandler.setAccelerationX(0);
				this.mPhysicsHandler.setVelocityX(0);
			}
			else if(!movingRight && this.mPhysicsHandler.getVelocityX() > 0){
				this.mPhysicsHandler.setAccelerationX(0);
				this.mPhysicsHandler.setVelocityX(0);
			}
			
			if(this.mPhysicsHandler.getVelocityX() > -1 && this.mPhysicsHandler.getVelocityX() < 1){
				this.mPhysicsHandler.setAccelerationX(0);
				this.mPhysicsHandler.setVelocityX(0);
				
				int the_mod = (int)(this.getX() - itemWidth/2) % itemWidth;
				if(the_mod != 0){
					if(the_mod < -itemWidth/2){
						// Floor
						this.setX((int)Math.floor((this.getX() - itemWidth/2) / itemWidth) * itemWidth + itemWidth/2 + paddingLeft);
					}
					else{
						this.setX((int)Math.ceil((this.getX() - itemWidth/2) / itemWidth) * itemWidth + itemWidth/2 + paddingLeft);
					}
				}
			}
			
			if(this.getX() > 0 + itemWidth/2 + paddingLeft){
				this.setX(0 + itemWidth/2 + paddingLeft);
			}
			else if(this.getX() < (-itemWidth * (itemCount-1)) + itemWidth/2 + paddingLeft){
				this.setX((-itemWidth * (itemCount-1)) + itemWidth/2 + paddingLeft);
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
