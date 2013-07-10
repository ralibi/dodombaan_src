package com.ralibi.dodombaan.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.os.Vibrator;

import com.ralibi.dodombaan.MainActivity;

public class ResourcesManager {
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private static final ResourcesManager INSTANCE = new ResourcesManager();

	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public float screenRatio;
	public Vibrator vibrator;

	public String fontText = "OpenSans-Regular.ttf";
	public String fontName = "AlfaSlabOne-Regular-OTF.otf";
	public String fontIconName = "fontawesome-webfont.ttf";
	// public String fontScoreName = "OpenSans-Regular.ttf";
	public String fontScoreName = "AlfaSlabOne-Regular-OTF.otf";

	// GENERAL
	private final int ramCount = 6;
	private final int arenaCount = 3;

	// ---------------------------------------------
	// TEXTURES & TEXTURE REGIONS
	// ---------------------------------------------
	private BuildableBitmapTextureAtlas mainBackgroundTextureAtlas;
	private BuildableBitmapTextureAtlas splashTextureAtlas;
	private BuildableBitmapTextureAtlas supportingObjectTextureAtlas;
	private BuildableBitmapTextureAtlas supportingObjectTextureAtlas2;
	private BuildableBitmapTextureAtlas wikiHelpContentTextureAtlas;
	private BuildableBitmapTextureAtlas panelOverlayTextureAtlas;
	private BuildableBitmapTextureAtlas buttonTextureAtlas;
	private BuildableBitmapTextureAtlas buttonTextureAtlas2;

	private BuildableBitmapTextureAtlas ramSelectionTextureAtlas;

	private BuildableBitmapTextureAtlas careerMenuTextureAtlas;
	private BuildableBitmapTextureAtlas exerciseMenuTextureAtlas;
	private BuildableBitmapTextureAtlas championshipMenuTextureAtlas;

	private BuildableBitmapTextureAtlas multiplayerMenuTextureAtlas,
	    multiplayerSingleDeviceMenuTextureAtlas,
	    multiplayerBluetoothMenuTextureAtlas,
	    multiplayerChampionshipMenuTextureAtlas;
	private BuildableBitmapTextureAtlas hostMenuTextureAtlas,
	    clientMenuTextureAtlas;

	public ITextureRegion championshipIconRegion;
	public ITextureRegion exerciseIconRegion;

	private BuildableBitmapTextureAtlas menuTextureAtlas;

	public ITextureRegion ramScrollBackgroundRegion;
	public ITextureRegion ramScrollToolbarRegion;

	public ITextureRegion powerBlankRegion;
	public ITextureRegion powerFilledRegion;

	public ITextureRegion strengthThumbRegion;
	public ITextureRegion speedThumbRegion;
	public ITextureRegion agilityThumbRegion;

	public List<ITextureRegion> ramSelectionRamRegions = new ArrayList<ITextureRegion>();

	// MatchSettingsScene
	public ITextureRegion arenaScrollBackgroundRegion;
	public List<ITextureRegion> matchSettingsArenaRegions = new ArrayList<ITextureRegion>();
	private BuildableBitmapTextureAtlas matchSettingsTextureAtlas;

	// GamePlayScene
	public List<ITextureRegion> gamePlayPlayingBackgroundRegions = new ArrayList<ITextureRegion>();
	public ITextureRegion overlayRegion;
	public ITextureRegion overlayBlackRegion;
	public ITextureRegion pauseOverlayRegion;

	public ITextureRegion wikiContentRegion;
	public ITextureRegion helpContentRegion;

	// public ITextureRegion gamePlayNextRoundRegion;

	public List<ITextureRegion> gamePlayRamSegment1Regions = new ArrayList<ITextureRegion>();
	public List<ITextureRegion> gamePlayRamSegment2Regions = new ArrayList<ITextureRegion>();
	public List<ITextureRegion> gamePlayRamSegment3Regions = new ArrayList<ITextureRegion>();

	public List<ITextureRegion> gamePlayRamDropRegions = new ArrayList<ITextureRegion>();
	public List<ITextureRegion> gamePlayRamThumbRegions = new ArrayList<ITextureRegion>();

	public List<ITextureRegion> gamePlayRubberRegions = new ArrayList<ITextureRegion>();
	public List<ITextureRegion> gamePlayRubberVibratingRegions = new ArrayList<ITextureRegion>();
	public List<TiledTextureRegion> nailNormalRegions = new ArrayList<TiledTextureRegion>();

	public ITextureRegion gamePlayRubberShadowRegion;
	public ITextureRegion gamePlayRubberVibratingShadowRegion;

	public ITextureRegion starBlank;
	public ITextureRegion starFilled;

	private BuildableBitmapTextureAtlas gamePlayTextureAtlas;

	// MatchOverScene
	// public ITextureRegion matchOverBackgroundRegion;

	private BuildableBitmapTextureAtlas matchOverTextureAtlas;

	// SettingsScene
	// public ITextureRegion settingsBackgroundRegion;
	public List<ITextureRegion> totalScrollRegions = new ArrayList<ITextureRegion>();

	private BuildableBitmapTextureAtlas settingsTextureAtlas;

	// shared

	// SHARED RESOURCE
	public ITextureRegion baseBackgroundRegion;
	public ITextureRegion matchOverBackgroundRegion;
	public ITextureRegion bannerTopBackgroundRegion;
	public ITextureRegion bannerBottomBackgroundRegion;

	public ITextureRegion fullPanelRegion;

	public ITextureRegion[] singleDeviceBattleButtonRegions = { null, null, null };
	public ITextureRegion[] multiplayerButtonRegions = { null, null, null };

	public ITextureRegion[] careerButtonRegions = { null, null, null };
	public ITextureRegion[] myRamsButtonRegions = { null, null, null };
	public ITextureRegion[] myFriendsButtonRegions = { null, null, null };

	public ITextureRegion[] settingsButtonRegions = { null, null, null };
	public ITextureRegion[] wikiButtonRegions = { null, null, null };
	public ITextureRegion[] helpButtonRegions = { null, null, null };

	public ITextureRegion[] backButtonRegions = { null, null, null };
	public ITextureRegion[] nextButtonRegions = { null, null, null };

	public ITextureRegion[] exerciseButtonRegions = { null, null, null };
	public ITextureRegion[] championshipButtonRegions = { null, null, null };

	public ITextureRegion[] navLeftButtonRegions = { null, null, null };
	public ITextureRegion[] navRightButtonRegions = { null, null, null };
	public ITextureRegion[] selectButtonRegions = { null, null, null };

	public ITextureRegion[] mainMenuButtonRegions = { null, null, null };
	public ITextureRegion[] rematchButtonRegions = { null, null, null };
	public ITextureRegion[] changeRamButtonRegions = { null, null, null };

	public ITextureRegion[] yesButtonRegions = { null, null, null };
	public ITextureRegion[] noButtonRegions = { null, null, null };
	public ITextureRegion[] okButtonRegions = { null, null, null };

	public ITextureRegion[] pauseButtonRegions = { null, null, null };
	public ITextureRegion[] resumeButtonRegions = { null, null, null };
	public ITextureRegion[] quitButtonRegions = { null, null, null };

	public ITextureRegion[] singleDeviceButtonRegions = { null, null, null };
	public ITextureRegion[] bluetoothButtonRegions = { null, null, null };
	public ITextureRegion[] createChampionshipButtonRegions = { null, null, null };

	public ITextureRegion[] battleButtonRegions = { null, null, null };
	public ITextureRegion[] swimmingButtonRegions = { null, null, null };
	public ITextureRegion[] runningButtonRegions = { null, null, null };

	public ITextureRegion[] hostButtonRegions = { null, null, null };
	public ITextureRegion[] clientButtonRegions = { null, null, null };

	public ITextureRegion[] dryButtonRegions = { null, null, null };
	public ITextureRegion[] wetButtonRegions = { null, null, null };

	public ITextureRegion logoImageRegion;
	public ITextureRegion mainCharacter;
	public ITextureRegion secondaryCharacter;
	public ITextureRegion landMainCharacter;

	public ITextureRegion mudRegion;

	public ITextureRegion comboboxRegion;
	public ITextureRegion checkboxUncheckedRegion;
	public ITextureRegion checkboxCheckedRegion;

	// public ITextureRegion largePopupBackgroundRegion;

	// Fonts
	public Font font;
	public Font fontIcon;
	public Font fontSmall;
	public Font fontScore;

	public Music clickMusic;
	public Music swipeMusic;
	public Music shortWhistleMusic;
	public Music longWhistleMusic;
	public Music clappingHandMusic;
	public Music bgmMusic;

	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------

	// SplashScreen Methods
	// ###########################################

	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		mainBackgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		baseBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainBackgroundTextureAtlas, activity, "wood_background.png");
		textureAtlasBuilderException(this.mainBackgroundTextureAtlas);

		splashTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		logoImageRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "logo_image.png");
		textureAtlasBuilderException(this.splashTextureAtlas);
	}

	public void unloadSplashScreen() {
		// mainBackgroundTextureAtlas.unload();
		// splashBackgroundRegion = null;
	}

	// Shared Resources
	// ###########################################
	public void loadSharedResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		supportingObjectTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		bannerTopBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas, activity, "banner_top.png");
		bannerBottomBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas, activity, "banner_bottom.png");
		mainCharacter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas, activity, "rams/ram (1).png");
		secondaryCharacter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas, activity, "rams/ram (2).png");
		landMainCharacter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas, activity, "land.png");
		textureAtlasBuilderException(this.supportingObjectTextureAtlas);

		supportingObjectTextureAtlas2 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		matchOverBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas2, activity, "match_over_background.png");
		mudRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(supportingObjectTextureAtlas2, activity, "mud.png");
		textureAtlasBuilderException(this.supportingObjectTextureAtlas2);

		wikiHelpContentTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		wikiContentRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(wikiHelpContentTextureAtlas, activity, "content/wiki.png");
		helpContentRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(wikiHelpContentTextureAtlas, activity, "content/help.png");
		textureAtlasBuilderException(this.wikiHelpContentTextureAtlas);

		panelOverlayTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		fullPanelRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(panelOverlayTextureAtlas, activity, "full_panel.png");
		overlayBlackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(panelOverlayTextureAtlas, activity, "overlay_black_background.png");
		// largePopupBackgroundRegion =
		// BitmapTextureAtlasTextureRegionFactory.createFromAsset(panelOverlayTextureAtlas,
		// activity, "match_settings/arena_scroll_background.png");
		textureAtlasBuilderException(this.panelOverlayTextureAtlas);
	}

	private void loadButtonResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		buttonTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		buttonTextureAtlas2 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

		createButtonFromAsset(singleDeviceBattleButtonRegions, "single_device_battle");
		createButtonFromAsset(multiplayerButtonRegions, "multiplayer");
		createButtonFromAsset(careerButtonRegions, "career");
		createButtonFromAsset(myRamsButtonRegions, "my_rams");
		createButtonFromAsset(myFriendsButtonRegions, "my_friends");

		createButtonFromAsset(settingsButtonRegions, "settings");
		createButtonFromAsset(wikiButtonRegions, "wiki");
		createButtonFromAsset(helpButtonRegions, "help");

		createButtonFromAsset(backButtonRegions, "back");

		createButtonFromAsset(exerciseButtonRegions, "exercise");
		createButtonFromAsset(championshipButtonRegions, "championship");

		createButtonFromAsset(mainMenuButtonRegions, "main_menu");
		createButtonFromAsset(rematchButtonRegions, "rematch");
		createButtonFromAsset(changeRamButtonRegions, "change_ram");

		createButtonFromAsset(navLeftButtonRegions, "nav_left");
		createButtonFromAsset(navRightButtonRegions, "nav_right");
		createButtonFromAsset(selectButtonRegions, "select");

		createButtonFromAsset(nextButtonRegions, "next");
		createButtonFromAsset(yesButtonRegions, "yes");
		createButtonFromAsset(noButtonRegions, "no");
		createButtonFromAsset(okButtonRegions, "ok");

		createButtonFromAsset(pauseButtonRegions, "pause");
		createButtonFromAsset(resumeButtonRegions, "resume");
		createButtonFromAsset(quitButtonRegions, "quit");

		comboboxRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTextureAtlas, activity, "combobox.png");

		checkboxCheckedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTextureAtlas, activity, "checkbox_checked.png");
		checkboxUncheckedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTextureAtlas, activity, "checkbox_unchecked.png");

		createButtonFromAsset(singleDeviceButtonRegions, "single_device", buttonTextureAtlas2);
		createButtonFromAsset(bluetoothButtonRegions, "bluetooth", buttonTextureAtlas2);
		createButtonFromAsset(createChampionshipButtonRegions, "create_championship", buttonTextureAtlas2);

		createButtonFromAsset(battleButtonRegions, "battle", buttonTextureAtlas2);
		createButtonFromAsset(swimmingButtonRegions, "swimming", buttonTextureAtlas2);
		createButtonFromAsset(runningButtonRegions, "running", buttonTextureAtlas2);

		createButtonFromAsset(hostButtonRegions, "host", buttonTextureAtlas2);
		createButtonFromAsset(clientButtonRegions, "client", buttonTextureAtlas2);
		createButtonFromAsset(dryButtonRegions, "dry", buttonTextureAtlas2);
		createButtonFromAsset(wetButtonRegions, "wet", buttonTextureAtlas2);

		textureAtlasBuilderException(this.buttonTextureAtlas);
		textureAtlasBuilderException(this.buttonTextureAtlas2);
	}

	private void createButtonFromAsset(ITextureRegion[] buttonRegions, String buttonName) {
		buttonRegions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTextureAtlas, activity, "button/" + buttonName + " (1).png");
		buttonRegions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTextureAtlas, activity, "button/" + buttonName + " (2).png");
		buttonRegions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTextureAtlas, activity, "button/" + buttonName + " (3).png");
	}

	private void createButtonFromAsset(ITextureRegion[] buttonRegions, String buttonName, BuildableBitmapTextureAtlas buildableBitmapTextureAtlas) {
		buttonRegions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, activity, "button/" + buttonName + " (1).png");
		buttonRegions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, activity, "button/" + buttonName + " (2).png");
		buttonRegions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buildableBitmapTextureAtlas, activity, "button/" + buttonName + " (3).png");
	}

	private void loadSharedFonts() {
		FontFactory.setAssetBasePath("font/");

		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), fontName, (float) 50, true, Color.WHITE.getABGRPackedInt(), 2, Color.BLACK.getABGRPackedInt());
		font.load();

		final ITexture mainFontIconTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fontIcon = FontFactory.createFromAsset(activity.getFontManager(), mainFontIconTexture, activity.getAssets(), fontIconName, (float) 50, true, Color.GREEN.getABGRPackedInt());
		fontIcon.load();

		final ITexture mainFontSmallTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fontSmall = FontFactory.createFromAsset(activity.getFontManager(), mainFontSmallTexture, activity.getAssets(), fontText, (float) 12, true, Color.BLACK.getABGRPackedInt());
		fontSmall.load();

		final ITexture fontScoreIconTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fontScore = FontFactory.createStrokeFromAsset(activity.getFontManager(), fontScoreIconTexture, activity.getAssets(), fontScoreName, (float) 24, true, Color.WHITE.getABGRPackedInt(), 1, Color.BLACK.getABGRPackedInt());
		fontScore.load();
	}

	// MainMenu Methods
	// ###########################################

	public void loadMenuResources() {
		loadMenuAudio();
		loadSharedResources();
		loadButtonResources();
		loadSharedFonts();
		loadMenuGraphics();
	}

	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.menuTextureAtlas);
		
		
		

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		settingsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

		totalScrollRegions.clear();
		for (int i = 0; i < 9; i++) {
			totalScrollRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsTextureAtlas, activity, "total_round/total_round (" + (i + 1) + ").png"));
		}

		textureAtlasBuilderException(this.settingsTextureAtlas);
	}

	private void loadMenuAudio() {
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.clickMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "click.ogg");
			this.swipeMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "swipe.ogg");
			this.shortWhistleMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "short_whistle.ogg");
			this.longWhistleMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "long_whistle.ogg");
			this.clappingHandMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "clapping_hand.ogg");
			this.bgmMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "kendang_penca.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
	}

	public void loadMenuTextures() {
		menuTextureAtlas.load();
	}

	public void unloadMenuTextures() {
		menuTextureAtlas.unload();
	}


	// RamSelection Methods
	// ###########################################

	public void loadRamSelectionResources() {
		loadRamSelectionGraphics();
	}

	private void loadRamSelectionGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		ramSelectionTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

		ramScrollBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/ram_scroll_background.png");
		ramScrollToolbarRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/ram_scroll_toolbar.png");

		powerBlankRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/power_blank.png");
		powerFilledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/power_filled.png");

		strengthThumbRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/strength_thumb.png");
		speedThumbRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/speed_thumb.png");
		agilityThumbRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/agility_thumb.png");

		ramSelectionRamRegions.clear();
		for (int i = 0; i < ramCount; i++) {
			ramSelectionRamRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(ramSelectionTextureAtlas, activity, "ram_selection/rams/ram (" + (i + 1) + ").png"));
		}

		textureAtlasBuilderException(this.ramSelectionTextureAtlas);
	}

	public void unloadRamSelectionTextures() {
		// TODO Auto-generated method stub
	}

	// MatchSettings Methods
	// ###########################################

	public void loadMatchSettingsResources() {
		loadMatchSettingsGraphics();
	}

	private void loadMatchSettingsGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		matchSettingsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

		arenaScrollBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchSettingsTextureAtlas, activity, "match_settings/arena_scroll_background.png");

		matchSettingsArenaRegions.clear();
		for (int i = 0; i < arenaCount; i++) {
			matchSettingsArenaRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchSettingsTextureAtlas, activity, "match_settings/arenas/arena (" + (i + 1) + ").png"));
		}

		textureAtlasBuilderException(this.matchSettingsTextureAtlas);
	}

	public void unloadMatchSettingsTextures() {
		// TODO Auto-generated method stub
	}

	// Career Menu Method
	// ###########################################

	public void loadCareerMenuResources() {
		loadCareerMenuGraphics();
	}

	private void loadCareerMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		careerMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

		championshipIconRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(careerMenuTextureAtlas, activity, "career_menu/championship.png");
		exerciseIconRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(careerMenuTextureAtlas, activity, "career_menu/exercise.png");

		textureAtlasBuilderException(this.careerMenuTextureAtlas);
	}

	public void unloadCareerMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Exercise Menu Method
	// ###########################################

	public void loadExerciseMenuResources() {
		loadExerciseMenuGraphics();
	}

	private void loadExerciseMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		exerciseMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.exerciseMenuTextureAtlas);
	}

	public void unloadExerciseMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Championship Menu Method
	// ###########################################

	public void loadChampionshipMenuResources() {
		loadChampionshipMenuGraphics();
	}

	private void loadChampionshipMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		championshipMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.championshipMenuTextureAtlas);
	}

	public void unloadChampionshipMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Multiplayer Menu Method
	// ###########################################

	public void loadMultiplayerMenuResources() {
		loadMultiplayerMenuGraphics();
	}

	private void loadMultiplayerMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		multiplayerMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.multiplayerMenuTextureAtlas);
	}

	public void unloadMultiplayerMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Multiplayer Single Device Menu Method
	// ###########################################

	public void loadMultiplayerSingleDeviceMenuResources() {
		loadMultiplayerSingleDeviceMenuGraphics();
	}

	private void loadMultiplayerSingleDeviceMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		multiplayerSingleDeviceMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.multiplayerSingleDeviceMenuTextureAtlas);
	}

	public void unloadMultiplayerSingleDeviceMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Multiplayer Bluetooth Menu Method
	// ###########################################

	public void loadMultiplayerBluetoothMenuResources() {
		loadMultiplayerBluetoothMenuGraphics();
	}

	private void loadMultiplayerBluetoothMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		multiplayerBluetoothMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.multiplayerBluetoothMenuTextureAtlas);
	}

	public void unloadMultiplayerBluetoothMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Multiplayer Championship Menu Method
	// ###########################################

	public void loadMultiplayerChampionshipMenuResources() {
		loadMultiplayerChampionshipMenuGraphics();
	}

	private void loadMultiplayerChampionshipMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		multiplayerChampionshipMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.multiplayerChampionshipMenuTextureAtlas);
	}

	public void unloadMultiplayerChampionshipMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Host Menu Method
	// ###########################################

	public void loadHostMenuResources() {
		loadHostMenuGraphics();
	}

	private void loadHostMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		hostMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.hostMenuTextureAtlas);
	}

	public void unloadHostMenuTextures() {
		// TODO Auto-generated method stub
	}

	// Client Menu Method
	// ###########################################

	public void loadClientMenuResources() {
		loadClientMenuGraphics();
	}

	private void loadClientMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		clientMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		textureAtlasBuilderException(this.clientMenuTextureAtlas);
	}

	public void unloadClientMenuTextures() {
		// TODO Auto-generated method stub
	}

	// GamePlay Methods
	// ###########################################

	public void loadGamePlayResources() {
		loadGamePlayGraphics();
		loadGamePlayFonts();
		loadGamePlayAudio();
	}

	private void loadGamePlayGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game_play/");
		gamePlayTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

		gamePlayPlayingBackgroundRegions.clear();
		for (int i = 0; i < arenaCount; i++) {
			gamePlayPlayingBackgroundRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "gameplay_background (" + (i + 1) + ").png"));
		}

		starBlank = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "star_blank.png");
		starFilled = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "star_filled.png");

		// gamePlayNailRegion =
		// BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas,
		// activity, "nail.png");

		// gamePlayRamSegmentRegions.clear();
		gamePlayRamSegment1Regions.clear();
		gamePlayRamSegment2Regions.clear();
		gamePlayRamSegment3Regions.clear();
		gamePlayRamDropRegions.clear();
		gamePlayRamThumbRegions.clear();
		for (int i = 0; i < ramCount; i++) {
			// gamePlayRamSegmentRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas,
			// activity, "segments/segment (" + (i + 1) + ").png"));
			gamePlayRamSegment1Regions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segments/segment (" + (i + 1) + ") (1).png"));
			gamePlayRamSegment2Regions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segments/segment (" + (i + 1) + ") (2).png"));
			gamePlayRamSegment3Regions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segments/segment (" + (i + 1) + ") (3).png"));
			gamePlayRamDropRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "ram_drop/ram_drop (" + (i + 1) + ").png"));
			gamePlayRamThumbRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "ram_thumb/thumb (" + (i + 1) + ").png"));
		}

		gamePlayRubberRegions.clear();
		gamePlayRubberVibratingRegions.clear();
		for (int i = 0; i < arenaCount; i++) {
			gamePlayRubberRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "rubber/rubber (" + (i + 1) + ").png"));
			gamePlayRubberVibratingRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "rubber/rubber_vibrating (" + (i + 1) + ").png"));
		}

		gamePlayRubberShadowRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "rubber/rubber_shadow.png");
		gamePlayRubberVibratingShadowRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "rubber/rubber_vibrating_shadow.png");

		nailNormalRegions.clear();
		for (int i = 0; i < 5; i++) {
			ITextureRegion normalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "nail/normal/nail (" + (i + 1) + ").png");
			ITextureRegion pressedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "nail/pressed/nail (" + (i + 1) + ").png");
			nailNormalRegions.add(new TiledTextureRegion(normalTextureRegion.getTexture(), normalTextureRegion, pressedTextureRegion));
		}

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		overlayRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "overlay_background.png");
		pauseOverlayRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "match_settings/arena_scroll_background.png");

		textureAtlasBuilderException(this.gamePlayTextureAtlas);
	}

	private void loadGamePlayFonts() {

	}

	private void loadGamePlayAudio() {

	}

	public void unloadGamePlayTextures() {
		// TODO Auto-generated method stub
	}

	// MatchOver Methods
	// ###########################################

	public void loadMatchOverResources() {
		loadMatchOverGraphics();
	}

	private void loadMatchOverGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/match_over/");
		matchOverTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

		textureAtlasBuilderException(this.matchOverTextureAtlas);
	}

	public void unloadMatchOverTextures() {
		// TODO Auto-generated method stub
	}

	/**
	 * @param engine
	 * @param activity
	 * @param camera
	 * @param vbom
	 * <br>
	 * <br>
	 *          We use this method at beginning of game loading, to prepare
	 *          Resources Manager properly, setting all needed parameters, so we
	 *          can latter access them from different classes (eg. scenes)
	 */
	public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		getInstance().vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		getInstance().screenRatio = getInstance().camera.getSurfaceWidth() / getInstance().camera.getWidth();
	}

	// ---------------------------------------------
	// GETTERS AND SETTERS
	// ---------------------------------------------

	public static ResourcesManager getInstance() {
		return INSTANCE;
	}

	// GENERAL METHODS

	private void textureAtlasBuilderException(BuildableBitmapTextureAtlas buildableBitmapTextureAtlas) {
		try {
			buildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			buildableBitmapTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
}
