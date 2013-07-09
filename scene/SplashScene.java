package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class SplashScene extends BaseScene {
	
	@Override
	public void createScene() {
    attachChild(new Sprite(400,  240, resourcesManager.baseBackgroundRegion, vbom)
    {
      @Override
        protected void preDraw(GLState pGLState, Camera pCamera) 
        {
           super.preDraw(pGLState, pCamera);
           pGLState.enableDither();
        }
    });

    Sprite logoImage = new Sprite(400,  250, resourcesManager.logoImageRegion, vbom);
    
    attachChild(logoImage);
    // attachChild(logoText);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
    // TODO Auto-generated method stub
	}

	@Override
	public void unTouchScrollMenu() {
		// TODO Auto-generated method stub
		
	}

}
