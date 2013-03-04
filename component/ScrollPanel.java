package com.ralibi.dodombaan.component;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;

import com.ralibi.dodombaan.manager.ResourcesManager;

public class ScrollPanel extends Entity {
	
	public PhysicsHandler mPhysicsHandler;
	public boolean isMovingRight = false;
	
//	public ScrollPanel(float x, float y, int width, int height) {
//		super(x, y);
//		this.setWidth(width);
//		this.setHeight(height);
//		
//		this.mPhysicsHandler = new PhysicsHandler(this);
//		this.registerUpdateHandler(this.mPhysicsHandler);
//		this.mPhysicsHandler.setVelocity(3, 0);
//	}
//	
	public ScrollPanel() {
		super();
		
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
		this.mPhysicsHandler.setVelocity(10, 0);
	}

	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		// this.setX(this.getX() + 1);
		super.onManagedUpdate(pSecondsElapsed);
		
		if(!ResourcesManager.getInstance().touching){
			if(isMovingRight && this.mPhysicsHandler.getVelocityX() < 0){
				this.mPhysicsHandler.setAccelerationX(0);
				this.mPhysicsHandler.setVelocityX(0);
			}
			else if(!isMovingRight && this.mPhysicsHandler.getVelocityX() > 0){
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
}
