package com.ralibi.dodombaan.component;

import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import com.ralibi.dodombaan.manager.ResourcesManager;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;


public class ScrollEntity extends ClippingEntity {


	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	protected float ratio = ResourcesManager.getInstance().camera.getSurfaceHeight() / ResourcesManager.getInstance().camera.getHeight();
	private float mTouchX = 0, mTouchOffsetX = 0;
	// private float mTouchY = 0, mTouchOffsetY = 0;
	private int size = 0;
	private int itemWidth = 0;
	private int itemHeight = 0;
	
	private GestureDetector mDetector;
	private ScrollPanel scrollPanel;
	

	//---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

	public ScrollEntity(float x, float y, int width, int height, ScrollPanel pScrollPanel) {
		super(x, y, width, height);
		setScrollPanel(pScrollPanel);
		
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

			@SuppressWarnings("deprecation")
			public void run() {
				mDetector = new GestureDetector(new SimpleOnGestureListener(){
					public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
					{     
						float newVelocityX = 0;
						if(velocityX > 2000) newVelocityX = 2000;
						if(velocityX < -2000) newVelocityX = -2000;
						scrollPanel.getmPhysicsHandler().setVelocityX(newVelocityX / ratio);
						if(velocityX > 0){
							scrollPanel.setMovingRight(true);
							scrollPanel.getmPhysicsHandler().setAccelerationX(-2000);
						}
						else{
							scrollPanel.setMovingRight(false);
							scrollPanel.getmPhysicsHandler().setAccelerationX(2000);
						}
						Debug.d("fliiiing");
						return true;
					}
				});
			}
		});

		//this.mDetector = new GestureDetector(new SimpleOnGestureListener());
	}



	//---------------------------------------------
    // GETTERS SETTERS
    //---------------------------------------------
	
	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}

	public void setScrollPanel(ScrollPanel scrollPanel) {
		this.scrollPanel = scrollPanel;
	}
	
	public int getSelectedMenuIndex() {
		return size + Math.round((getScrollPanel().getX() - getWidth() / 2) / itemWidth);
	}
	
	
	
	//---------------------------------------------
    // METHODS
    //---------------------------------------------
	public void createMenus() {
		
	}
	
	
	
	//---------------------------------------------
    // OVERRIDES
    //---------------------------------------------
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {


		this.mDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());

		Debug.d("Touch screen");
		
		switch(pSceneTouchEvent.getAction()){
		case TouchEvent.ACTION_MOVE:{
			scrollPanel.setTouching(true);
			float newX = pSceneTouchEvent.getMotionEvent().getX();
			// float newY = pSceneTouchEvent.getMotionEvent().getY();

			mTouchOffsetX = (newX - mTouchX) / ratio;
			//mTouchOffsetY = -(newY - mTouchY) / ratio;

			mTouchX = newX;
			// mTouchY = newY;

			float x = scrollPanel.getX() + mTouchOffsetX;
			// float y = contentEntity.getY() + mTouchOffsetY;

			if(x > 0 + itemWidth){
				x = 0 + itemWidth;
			}
			else if(x < -itemWidth * size){
				x = -itemWidth * size;
			}

			scrollPanel.setPosition(x, scrollPanel.getY());
			return true;
		}
		case TouchEvent.ACTION_DOWN:{
			scrollPanel.setTouching(true);
			Debug.d("Scroll down");
			scrollPanel.getmPhysicsHandler().setVelocityX(0);
			scrollPanel.getmPhysicsHandler().setAccelerationX(0);
			mTouchX = pSceneTouchEvent.getMotionEvent().getX();
			// mTouchY = pSceneTouchEvent.getMotionEvent().getY();
			return true; 
		}
		case TouchEvent.ACTION_UP:{
			scrollPanel.setTouching(false);
			return true; 
		}
		default:{
			scrollPanel.setTouching(false);
			return false;
		}
		}
	}



	public void buildSprite(int textureWidth, int textureHeight, List<ITextureRegion> textureRegions, VertexBufferObjectManager vbom) {
		size = textureRegions.size();
		itemWidth = textureWidth;
		itemHeight = textureHeight;
		for (int i = 0; i < size; i++) {
			Sprite sprite = new Sprite(0 + i * itemWidth, itemHeight / 2, textureRegions.get(i), vbom){		
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					return true;
				}
			};
			scrollPanel.attachChild(sprite);
		}
	}

}
