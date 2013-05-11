package com.ralibi.dodombaan.component;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;


public class ScrollPanel extends Entity {

	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	private PhysicsHandler mPhysicsHandler;
	private boolean movingRight = false;
	private boolean touching = false;

	private int itemCount = 0;
	private int itemWidth = 0;
	private int itemHeight = 0;
	private int currentIndex = 0;
	private int selectedIndex = -1;
	
	private int parentWidth = 0;
	float paddingLeft;

	//---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------




	public ScrollPanel(int width, int height) {
		super();
		
		this.mPhysicsHandler = new PhysicsHandler(this);
		parentWidth = width;
		paddingLeft = (parentWidth-itemWidth)/2;
		this.registerUpdateHandler(this.mPhysicsHandler);
	}
	
	
	
	//---------------------------------------------
    // GETTERS SETTERS
    //---------------------------------------------

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public void setSelectedIndex(int selectedIndex) {
    this.selectedIndex = selectedIndex;
    if(selectedIndex >= 0){
      this.currentIndex = selectedIndex;
      this.setX(getSnapX(selectedIndex));
    }
  }
  
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
    paddingLeft = (parentWidth-itemWidth)/2;
	}

	public int getItemHeight() {
		return itemHeight;
	}

	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public void navigate(int direction){
		switch (direction) {
		case -1:
		  if(getCurrentIndex() > 0){
        setMovingRight(true);
        this.mPhysicsHandler.setVelocityX(itemWidth * 2);
        currentIndex -= 1;
		  }
			break;

		case 1:
      if(getCurrentIndex() < getItemCount() - 1){
        setMovingRight(false);
        this.mPhysicsHandler.setVelocityX(itemWidth * -2);
        currentIndex += 1;
      }
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
			float posMinX = paddingLeft;
			float posMaxX = ((1-itemCount) * itemWidth) + paddingLeft;
			float posSnapX = getSnapX(currentIndex);
			

			if(this.mPhysicsHandler.getVelocityX() == 0){
				if(this.getX() > posSnapX){
					if(this.getX() - posSnapX > itemWidth/2 && currentIndex > 0){
						setMovingRight(true);
						this.mPhysicsHandler.setVelocityX(itemWidth * 2);
						currentIndex -= 1;
					}
					else{
						setMovingRight(false);
						this.mPhysicsHandler.setVelocityX(-itemWidth * 2);
					}
					
				}else if(this.getX() < posSnapX){
					if(posSnapX - this.getX() > itemWidth/2 && currentIndex < itemCount - 1){
						setMovingRight(false);
						this.mPhysicsHandler.setVelocityX(-itemWidth * 2);
						currentIndex += 1;
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
				currentIndex = 0;
			}
			else if(this.getX() < posMaxX){
				this.setX(posMaxX);
				currentIndex = itemCount - 1;
			}
			
		}
	}

	private float getSnapX(int index){
	  return (-index * itemWidth) + paddingLeft;
	}
}
