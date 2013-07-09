package com.ralibi.dodombaan;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;

import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import com.ralibi.dodombaan.manager.ResourcesManager;
import com.ralibi.dodombaan.manager.SceneManager;

public class MainActivity extends BaseGameActivity implements
IOnSceneTouchListener {

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	// private static final String EXAMPLE_UUID =
	// "6c7067e0-88aa-11e2-9e96-0800200c9a66";
	// private static final String LOCALHOST_IP = "127.0.0.1";
	// private static final int SERVER_PORT = 4444;
	//
	// public static final short FLAG_MESSAGE_SERVER_MOVE_SHEEP = 1;
	//
	//
	//
	// public static final short FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE =
	// Short.MIN_VALUE;
	// public static final short FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH =
	// FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE + 1;
	// public static final short FLAG_MESSAGE_CLIENT_CONNECTION_PING =
	// FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH + 1;
	//
	// public static final short FLAG_MESSAGE_SERVER_CONNECTION_CLOSE =
	// Short.MIN_VALUE;
	// public static final short FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED =
	// FLAG_MESSAGE_SERVER_CONNECTION_CLOSE + 1;
	// public static final short
	// FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH =
	// FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED + 1;
	// public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG =
	// FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH + 1;
	//
	//
	//
	// public String mServerIP = LOCALHOST_IP;
	// public SocketServer<SocketConnectionClientConnector> mSocketServer;
	// public ServerConnector<SocketConnection> mServerConnector;
	//
	// public final MessagePool<IMessage> mMessagePool = new
	// MessagePool<IMessage>();
	//

	private Camera camera;

	// private ResourcesManager resourcesManager;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, 800, 480);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						800, 480), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

		// MainActivity.this.initServerAndClient();

		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
					throws IOException {

		ResourcesManager.prepareManager(mEngine, this, camera,
				getVertexBufferObjectManager());
		// resourcesManager = ResourcesManager.getInstance();
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
					throws IOException {
		mEngine.registerUpdateHandler(new TimerHandler(1f,
				new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMainMenuScene();
				// set menu scene using scene manager
				// disposeSplashScene();
				// READ NEXT ARTICLE FOR THIS PART.

			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			// Debug.d("Activity DOWN");
			return true;
		case TouchEvent.ACTION_MOVE:
			SceneManager.getInstance().getCurrentScene().unTouchScrollMenu();
			return true;
		case TouchEvent.ACTION_UP:
			SceneManager.getInstance().getCurrentScene().unTouchScrollMenu();
			return true;
		}
		return false;
	}

}
