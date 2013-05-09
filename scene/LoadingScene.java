package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
	  createBackground();
		attachChild(new Text(400, 240, resourcesManager.font, "Loading...", vbom));
	}
	
  private void createBackground() {
    attachChild(new Sprite(400,  240, resourcesManager.baseBackgroundRegion, vbom)
    {
      @Override
        protected void preDraw(GLState pGLState, Camera pCamera) 
        {
           super.preDraw(pGLState, pCamera);
           pGLState.enableDither();
        }
    });
  }
  

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
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
