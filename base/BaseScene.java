package com.ralibi.dodombaan.base;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.ralibi.dodombaan.MainActivity;
import com.ralibi.dodombaan.manager.GameDataManager;
import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public abstract class BaseScene extends Scene {
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    protected Engine engine;
    protected MainActivity activity;
    protected ResourcesManager resourcesManager;
    protected GameDataManager gameDataManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    
    protected float CAMERA_WIDTH;
    protected float CAMERA_HEIGHT;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene()
    {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        this.gameDataManager = GameDataManager.getInstance();
        this.setOnSceneTouchListener(ResourcesManager.getInstance().activity);

        this.CAMERA_WIDTH = this.camera.getWidth();
        this.CAMERA_HEIGHT = this.camera.getHeight();
        
        createScene();
    }
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();

    public abstract void unTouchScrollMenu();
    
    public abstract SceneType getSceneType();
    
    public abstract void disposeScene();
    
    
}
