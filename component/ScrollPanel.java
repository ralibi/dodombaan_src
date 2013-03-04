package com.ralibi.dodombaan.component;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;

import android.location.Address;

import com.ralibi.dodombaan.manager.ResourcesManager;

public class ScrollPanel extends Entity implements IOnMenuItemClickListener {

	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	private PhysicsHandler mPhysicsHandler;
	private boolean movingRight = false;
	private boolean touching = false;

	private MenuScene menuChildScene;

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
				
				int the_mod = (int)(this.getX() - 100) % 200;
				if(the_mod != 0){
					if(the_mod < -100){
						// Floor
						this.setX((int)Math.floor((this.getX() - 100.0) / 200.0) * 200 + 100 + 25);
					}
					else{
						this.setX((int)Math.ceil((this.getX() - 100.0) / 200.0) * 200 + 100 + 25);
					}
				}
			}
			
			if(this.getX() > 0 + 100 + 25){
				this.setX(0 + 100 + 25);
			}
			else if(this.getX() < -200 * 5 + 100 + 25){
				this.setX(-200 * 5 + 100 + 25);
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
