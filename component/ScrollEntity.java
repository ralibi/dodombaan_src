package com.ralibi.dodombaan.component;
import org.andengine.entity.IEntity;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.util.debug.Debug;

import android.view.MotionEvent;


public class ScrollEntity extends ClippingEntity {

	private float mTouchX = 0, mTouchY = 0, mTouchOffsetX = 0, mTouchOffsetY = 0;
	
	public ScrollEntity(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

		Debug.d("Touch screen");
		if(pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			Debug.d("Scroll down");
			mTouchX = pSceneTouchEvent.getMotionEvent().getX();
            mTouchY = pSceneTouchEvent.getMotionEvent().getY();
            scrollDetector.setEnabled(true);
            scrollDetector.onTouchEvent(pSceneTouchEvent);
		}
		if(pSceneTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {
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
			IEntity contentEntity = this.getChildByIndex(0);
			
			float x = contentEntity.getX() + mTouchOffsetX;
			// float y = contentEntity.getY() + mTouchOffsetY;
			
			contentEntity.setPosition(x, contentEntity.getY());
			
		}
		return true;
	}




//	@Override
//	public void onScrollStarted(ScrollDetector detector, int pointerID, float distanceX, float distanceY) {
//		Debug.d("Scroll start");
//	}
//
//	@Override
//	public void onScroll(ScrollDetector detector, int pointerID, float distanceX, float distanceY) {
//		Debug.d("Scrolling");
//		IEntity contentEntity = this.getChildByIndex(0);
//		//if(contentEntity != null) {
//			float y = contentEntity.getY() + distanceY;
//			contentEntity.setPosition(contentEntity.getX(), y);
//		//}
//	}
//
//	@Override
//	public void onScrollFinished(ScrollDetector detector, int pointerID, float distanceX, float distanceY) {
//	}
}
