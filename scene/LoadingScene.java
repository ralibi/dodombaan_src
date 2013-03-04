package com.ralibi.dodombaan.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		setBackground(new Background(.6f, .6f, .6f));
		attachChild(new Text(400, 240, resourcesManager.font, "Loading...", vbom));
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

}
