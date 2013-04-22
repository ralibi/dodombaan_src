package com.ralibi.dodombaan.component;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import com.ralibi.dodombaan.manager.ResourcesManager;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;


public class ScrollMenuEntity extends ClippingEntity {


	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	
	protected float ratio = ResourcesManager.getInstance().camera.getSurfaceHeight() / ResourcesManager.getInstance().camera.getHeight();
	private float mTouchX = 0, mTouchOffsetX = 0;
	// private float mTouchY = 0, mTouchOffsetY = 0;

	private GestureDetector mDetector;
	private ScrollPanel scrollPanel;

	//---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

	public ScrollMenuEntity(float x, float y, int width, int height, ScrollPanel pScrollPanel) {
		super(x, y, width, height);
		setScrollPanel(pScrollPanel);
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
		return this.scrollPanel.getSelectedIndex();
	}

	
	//---------------------------------------------
    // METHODS
    //---------------------------------------------
	
	
	//---------------------------------------------
    // OVERRIDES
    //---------------------------------------------
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		this.mDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());

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
			float paddingLeft = (getWidth() - scrollPanel.getItemWidth()) / 2;
			
			if(x > paddingLeft){
				x = paddingLeft;
			}
			else if(x < -scrollPanel.getItemWidth() * (scrollPanel.getItemCount()-1) + paddingLeft){
				x = -scrollPanel.getItemWidth() * (scrollPanel.getItemCount()-1) + paddingLeft;
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
			return false; 
		}
		case TouchEvent.ACTION_UP:{
			scrollPanel.setTouching(false);
			Debug.d("UUUPPPPP");
			return true; 
		}
		default:{
			scrollPanel.setTouching(false);
			return true;
		}
		}
	}



	public void buildSprite(int textureX, int textureY, int textureWidth, int textureHeight, List<ITextureRegion> textureRegions, Scene pScene, VertexBufferObjectManager vbom) {
		scrollPanel.setItemCount(textureRegions.size());
		scrollPanel.setItemWidth(textureWidth);
		scrollPanel.setItemHeight(textureHeight);
		
		// Iteration for menu item
		for (int i = 0; i < scrollPanel.getItemCount(); i++) {
			Sprite sprite = new Sprite(textureX + i * scrollPanel.getItemWidth(), textureY, textureRegions.get(i), vbom){		
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// Debug.d("button menu TOUCHED");
					return false;
				}
			};
			scrollPanel.attachChild(sprite);
			pScene.registerTouchArea(sprite);
		}
		
		// creating new thread for gesture listener
		createNewThread();
	}



	private void createNewThread() {

		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

			public void run() {
				mDetector = new GestureDetector(ResourcesManager.getInstance().activity, new SimpleOnGestureListener(){
					public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
					{     
						float newVelocityX = 0;
						float vValue = scrollPanel.getItemWidth() * 10;
						if(velocityX > vValue) newVelocityX = vValue;
						if(velocityX < -vValue) newVelocityX = -vValue;
						scrollPanel.getmPhysicsHandler().setVelocityX(newVelocityX / ratio);
						
						
						float velo = scrollPanel.getItemWidth() * 2;
						if(velocityX > 0){
							// moving right means index decreasing 
							
							scrollPanel.setMovingRight(true);
							//scrollPanel.getmPhysicsHandler().setAccelerationX(-vValue);

							scrollPanel.getmPhysicsHandler().setAccelerationX(0);
							scrollPanel.getmPhysicsHandler().setVelocityX(velo);
							scrollPanel.setSelectedIndex(scrollPanel.getSelectedIndex() - 1);
						}
						else{
							scrollPanel.setMovingRight(false);
							//scrollPanel.getmPhysicsHandler().setAccelerationX(vValue);

							scrollPanel.getmPhysicsHandler().setAccelerationX(0);
							scrollPanel.getmPhysicsHandler().setVelocityX(-velo);
							scrollPanel.setSelectedIndex(scrollPanel.getSelectedIndex() + 1);
						}
						// Debug.d("fliiiing");
						return true;
					}
				});
			}
		});
		
	}

}
