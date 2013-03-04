package com.ralibi.dodombaan.component;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.manager.ResourcesManager;

import android.opengl.GLES20;

public class ClippingEntity extends Entity {

	protected float ratio = ResourcesManager.getInstance().camera.getSurfaceHeight() / ResourcesManager.getInstance().camera.getHeight();
	
	public ClippingEntity(float x, float y, int width, int height) {
		super(x, y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	@Override
    protected void onManagedDraw(final GLState glState, final Camera camera) {
		
        glState.pushProjectionGLMatrix();
        glState.enableScissorTest();
         //invert Y coordinate as needed by OpenGL
        final int x = (int)((getX() - getWidth()/2) * ratio);
        final int y = (int)((getY() - getHeight()/2) * ratio);
        // GLES20.glScissor((int)coords[0], y, (int) getWidth(),(int) getHeight());
        GLES20.glScissor(x, y, (int)(getWidth() * ratio), (int)(getHeight()* ratio));
//        Debug.d("y: " + y); // 306
//        Debug.d("x: " + x); // 306 
//        Debug.d("getY(): " + getY()); // 720
//        Debug.d("getX(): " + getX()); // 720
//        Debug.d("getHeight(): " + getHeight()); // 64
//        Debug.d("getWidth(): " + getWidth()); // 64
        super.onManagedDraw(glState, camera);
        glState.disableScissorTest();
        glState.popProjectionGLMatrix();
	}
	
}
