package com.ralibi.dodombaan.component;
import org.andengine.entity.IEntity;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.ralibi.dodombaan.manager.ResourcesManager;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;


public class ScrollEntity extends ClippingEntity {

	protected float ratio = ResourcesManager.getInstance().camera.getSurfaceHeight() / ResourcesManager.getInstance().camera.getHeight();

	private float mTouchX = 0, mTouchY = 0, mTouchOffsetX = 0, mTouchOffsetY = 0;
	private GestureDetector mDetector;
	private ScrollPanel scrollPanel;

	public ScrollEntity(float x, float y, int width, int height) {
		super(x, y, width, height);

		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

			@SuppressWarnings("deprecation")
			public void run() {
				mDetector = new GestureDetector(new SimpleOnGestureListener(){
					public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
					{     
						float newVelocityX = 0;
						if(velocityX > 2000) newVelocityX = 2000;
						if(velocityX < -2000) newVelocityX = -2000;
						scrollPanel.mPhysicsHandler.setVelocityX(newVelocityX / ratio);
						if(velocityX > 0){
							scrollPanel.isMovingRight = true;
							scrollPanel.mPhysicsHandler.setAccelerationX(-2000);
						}
						else{
							scrollPanel.isMovingRight = false;
							scrollPanel.mPhysicsHandler.setAccelerationX(2000);
						}
						Debug.d("fliiiing");
						return true;
					}
				});
			}
		});

		//this.mDetector = new GestureDetector(new SimpleOnGestureListener());
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

		scrollPanel = (ScrollPanel) this.getChildByIndex(0);

		this.mDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());

		Debug.d("Touch screen");
		
		switch(pSceneTouchEvent.getAction()){
		case TouchEvent.ACTION_MOVE:{
			ResourcesManager.getInstance().touching = true;
			float newX = pSceneTouchEvent.getMotionEvent().getX();
			float newY = pSceneTouchEvent.getMotionEvent().getY();

			mTouchOffsetX = (newX - mTouchX) / ratio;
			mTouchOffsetY = -(newY - mTouchY) / ratio;


			Debug.d("mTouchX" + mTouchX);
			Debug.d("mTouchY: " + mTouchY);
			Debug.d("newX: " + newX);
			Debug.d("newY: " + newY);

			mTouchX = newX;
			mTouchY = newY;


			float x = scrollPanel.getX() + mTouchOffsetX;
			// float y = contentEntity.getY() + mTouchOffsetY;


			if(x > 0 + 200){
				x = 0 + 200;
			}
			else if(x < -200 * 5){
				x = -200 * 5;
			}

			scrollPanel.setPosition(x, scrollPanel.getY());
			return true; // don't forget to break, or return true directly if the event was handled
		}
		case TouchEvent.ACTION_DOWN:{
			ResourcesManager.getInstance().touching = true;
			Debug.d("Scroll down");
			scrollPanel.mPhysicsHandler.setVelocityX(0);
			scrollPanel.mPhysicsHandler.setAccelerationX(0);
			mTouchX = pSceneTouchEvent.getMotionEvent().getX();
			mTouchY = pSceneTouchEvent.getMotionEvent().getY();
			return true; 
		}
		case TouchEvent.ACTION_UP:{
			// do stuff when the finger goes up again and ends the touch event (your case)
			ResourcesManager.getInstance().touching = false;
			return true; 
		}
		default:{
			// none of the above
			ResourcesManager.getInstance().touching = false;
			return false;
		}
		}


	}

}
