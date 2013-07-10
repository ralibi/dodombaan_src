package com.ralibi.dodombaan.component;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.manager.ResourcesManager;

import android.opengl.GLES20;

public class ClippingEntity extends Entity {

	protected float ratio = ResourcesManager.getInstance().camera.getSurfaceHeight() / ResourcesManager.getInstance().camera.getHeight();
	
	float xClip, yClip;
	
	public ClippingEntity(float x, float y, int width, int height) {
		super(width/2, height/2);
		this.setWidth(width);
		this.setHeight(height);
		this.xClip = x;
		this.yClip = y;
	}
	
	@Override
    protected void onManagedDraw(final GLState glState, final Camera camera) {
		
        glState.pushProjectionGLMatrix();
        glState.enableScissorTest();
        final int x = (int)((xClip - getWidth()/2) * ratio);
        final int y = (int)((yClip - getHeight()/2) * ratio);
        GLES20.glScissor(x, y, (int)(getWidth() * ratio), (int)(getHeight()* ratio));
        super.onManagedDraw(glState, camera);
        glState.disableScissorTest();
        glState.popProjectionGLMatrix();
	}
	
}
