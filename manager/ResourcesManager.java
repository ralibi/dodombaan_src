package com.ralibi.dodombaan.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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

  public String fontName = "OpenSans-Regular.ttf";
  public String fontIconName = "fontawesome-webfont.ttf";
  public String fontScoreName = "OpenSans-Regular.ttf";

  // GENERAL
  private final int sheepCount = 6;
  private final int arenaCount = 3;

  // ---------------------------------------------
  // TEXTURES & TEXTURE REGIONS
  // ---------------------------------------------

  // Splash Scene
  public ITextureRegion splashBackgroundRegion;
  private BitmapTextureAtlas splashTextureAtlas;

  // Menu Scene
  public ITextureRegion menuBackgroundRegion;
  public ITextureRegion multiplayerSingleDeviceRegion;
  public ITextureRegion multiplayerOverBluetoothRegion;
  public ITextureRegion settingsRegion;
  public ITextureRegion exitRegion;
  private BuildableBitmapTextureAtlas menuTextureAtlas;

  // SheepSelectionScene
  public ITextureRegion sheepSelectionBackgroundRegion;
  public ITextureRegion sheepSelectionNextRegion;
  public ITextureRegion sheepSelectionBackRegion;
  public List<ITextureRegion> sheepSelectionSheepRegions = new ArrayList<ITextureRegion>();
  private BuildableBitmapTextureAtlas sheepSelectionTextureAtlas;

  // MatchSettingsScene
  public ITextureRegion matchSettingsBackgroundRegion;
  public ITextureRegion matchSettingsNextRegion;
  public ITextureRegion matchSettingsBackRegion;
  public List<ITextureRegion> matchSettingsArenaRegions = new ArrayList<ITextureRegion>();
  private BuildableBitmapTextureAtlas matchSettingsTextureAtlas;

  // GamePlayScene
  public ITextureRegion gamePlayBackgroundRegion;
  public ITextureRegion gamePlayOverlayRegion;
  public ITextureRegion gamePlayP1WinRegion;
  public ITextureRegion gamePlayP2WinRegion;
  public ITextureRegion gamePlayPauseRegion;
  public ITextureRegion gamePlayResumeRegion;
  public ITextureRegion gamePlayExitToMenuRegion;
  public ITextureRegion gamePlayNextRoundRegion;
  public ITextureRegion gamePlayNextRegion;
  public List<ITextureRegion> gamePlaySheepSegmentRegions = new ArrayList<ITextureRegion>();
  public List<ITextureRegion> gamePlayPlayingArenaRegions = new ArrayList<ITextureRegion>();
  public List<TiledTextureRegion> nailNormalRegions = new ArrayList<TiledTextureRegion>();
  public ITextureRegion gamePlayIndicatorRegion;
  public ITextureRegion gamePlayIndicatorBackgroundRegion;
  public ITextureRegion gamePlayNailRegion;
  private BuildableBitmapTextureAtlas gamePlayTextureAtlas;

  // MatchOverScene
  public ITextureRegion matchOverBackgroundRegion;
  public ITextureRegion matchOverRematchRegion;
  public ITextureRegion matchOverChangeSheepRegion;
  public ITextureRegion matchOverBackToMenuRegion;
  public ITextureRegion matchOverExitRegion;
  private BuildableBitmapTextureAtlas matchOverTextureAtlas;

  // SettingsScene
  public ITextureRegion settingsBackgroundRegion;
  public ITextureRegion settingsOkRegion;
  public ITextureRegion settingsResetRegion;
  private BuildableBitmapTextureAtlas settingsTextureAtlas;

  // SHARED RESOURCE
  public ITextureRegion navLeftRegion;
  public ITextureRegion navRightRegion;

  public ITextureRegion selectNormalRegion;
  public ITextureRegion selectPressedRegion;
  public ITextureRegion selectDisabledRegion;

  public ITextureRegion nextNormalRegion;
  public ITextureRegion nextPressedRegion;
  public ITextureRegion nextDisabledRegion;
  
  public ITextureRegion backNormalRegion;
  public ITextureRegion backPressedRegion;
  public ITextureRegion backDisabledRegion;

  private BuildableBitmapTextureAtlas sharedTextureAtlas;

  // Fonts
  public Font font;
  public Font fontIcon;
  public Font fontSmall;
  public Font fontScore;

  public Sound majorSound;
  public Sound minorSound;

  // ---------------------------------------------
  // CLASS LOGIC
  // ---------------------------------------------

  // SplashScreen Methods
  // ###########################################

  public void loadSplashScreen() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");
    splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480, TextureOptions.BILINEAR);
    splashBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash_background.png", 0, 0);
    splashTextureAtlas.load();
  }

  public void unloadSplashScreen() {
    splashTextureAtlas.unload();
    splashBackgroundRegion = null;
  }

  // MainMenu Methods
  // ###########################################

  public void loadMenuResources() {
    loadMenuGraphics();
    loadMenuFonts();
    loadMenuAudio();
  }

  private void loadMenuGraphics() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu/menu_background.png");
    multiplayerSingleDeviceRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu/multiplayer_single_device.png");
    multiplayerOverBluetoothRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu/multiplayer_over_bluetooth.png");
    settingsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu/settings.png");
    exitRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "shared/exit.png");

    textureAtlasBuilderException(this.menuTextureAtlas);
  }

  private void loadMenuFonts() {
    FontFactory.setAssetBasePath("font/");
    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), fontName, (float) 50, true, Color.WHITE.getABGRPackedInt(), 2, Color.BLACK.getABGRPackedInt());
    font.load();

    final ITexture mainFontIconTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    fontIcon = FontFactory.createFromAsset(activity.getFontManager(), mainFontIconTexture, activity.getAssets(), fontIconName, (float) 50, true, Color.GREEN.getABGRPackedInt());
    fontIcon.load();

    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    sharedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

    navLeftRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/nav_left.png");
    navRightRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/nav_right.png");

    selectNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/select_normal.png");
    selectPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/select_pressed.png");
    selectDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/select_disabled.png");

    nextNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/next_normal.png");
    nextPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/next_pressed.png");
    nextDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/next_disabled.png");

    backNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/back_normal.png");
    backPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/back_pressed.png");
    backDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "shared/back_disabled.png");

    textureAtlasBuilderException(this.sharedTextureAtlas);
  }

  private void loadMenuAudio() {
    SoundFactory.setAssetBasePath("mfx/");
    try {
      this.majorSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "angklung_01.ogg");
      this.minorSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "angklung_02.ogg");
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

  // Settings Scene Methods

  public void loadSettingsResources() {
    loadSettingsGraphics();
  }

  private void loadSettingsGraphics() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    settingsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

    settingsBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsTextureAtlas, activity, "settings/settings_background.png");
    settingsOkRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsTextureAtlas, activity, "shared/ok.png");
    settingsResetRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsTextureAtlas, activity, "settings/reset.png");

    textureAtlasBuilderException(this.settingsTextureAtlas);
  }

  public void unloadSettingsTextures() {
    // TODO Auto-generated method stub
  }

  // SheepSelection Methods
  // ###########################################

  public void loadSheepSelectionResources() {
    loadSheepSelectionGraphics();
  }

  private void loadSheepSelectionGraphics() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    sheepSelectionTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

    sheepSelectionBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/sheep_selection_background.png");
    sheepSelectionNextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "shared/next.png");
    sheepSelectionBackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "shared/back.png");

    sheepSelectionSheepRegions.clear();
    for (int i = 0; i < sheepCount; i++) {
      sheepSelectionSheepRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/sheeps/sheep (" + (i + 1) + ").png"));
    }

    // load font
    FontFactory.setAssetBasePath("font/");
    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

    fontSmall = FontFactory.createFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), fontName, (float) 16, true, Color.BLACK.getABGRPackedInt());
    fontSmall.load();

    // load nav
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    navLeftRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "shared/nav_left.png");
    navRightRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "shared/nav_right.png");

    textureAtlasBuilderException(this.sheepSelectionTextureAtlas);
  }

  public void unloadSheepSelectionTextures() {
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

    matchSettingsBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchSettingsTextureAtlas, activity, "match_settings/match_settings_background.png");
    matchSettingsNextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchSettingsTextureAtlas, activity, "shared/next.png");
    matchSettingsBackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchSettingsTextureAtlas, activity, "shared/back.png");

    matchSettingsArenaRegions.clear();
    for (int i = 0; i < arenaCount; i++) {
      matchSettingsArenaRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchSettingsTextureAtlas, activity, "match_settings/arenas/arena (" + (i + 1) + ").png"));
    }

    textureAtlasBuilderException(this.matchSettingsTextureAtlas);
  }

  public void unloadMatchSettingsTextures() {
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

    gamePlayBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "game_play_background.png");
    gamePlayP1WinRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "p1_win.png");
    gamePlayP2WinRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "p2_win.png");
    gamePlayPauseRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "pause.png");
    gamePlayResumeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "resume.png");
    gamePlayExitToMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "exit_to_menu.png");
    gamePlayNextRoundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "next_round.png");

    gamePlayIndicatorRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "indicator.png");
    gamePlayIndicatorBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "indicator_background.png");

    gamePlayNailRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "nail.png");

    gamePlaySheepSegmentRegions.clear();
    for (int i = 0; i < sheepCount; i++) {
      gamePlaySheepSegmentRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segment (" + (i + 1) + ").png"));
    }

    gamePlayPlayingArenaRegions.clear();
    for (int i = 0; i < arenaCount; i++) {
      gamePlayPlayingArenaRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "playing_arena (" + (i + 1) + ").png"));
    }

    nailNormalRegions.clear();
    for (int i = 0; i < 5; i++) {
      ITextureRegion normalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "nail/nail_normal_" + (i + 1) + ".png");
      ITextureRegion pressedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "nail/nail_pressed_" + (i + 1) + ".png");
      nailNormalRegions.add(new TiledTextureRegion(normalTextureRegion.getTexture(), normalTextureRegion, pressedTextureRegion));
    }

    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/shared/");
    gamePlayOverlayRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "overlay_background.png");
    gamePlayNextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "next.png");

    final ITexture fontScoreIconTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    fontScore = FontFactory.createFromAsset(activity.getFontManager(), fontScoreIconTexture, activity.getAssets(), fontScoreName, (float) 50, true, Color.GREEN.getABGRPackedInt());
    fontScore.load();

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

    matchOverBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchOverTextureAtlas, activity, "match_over_background.png");
    matchOverRematchRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchOverTextureAtlas, activity, "rematch.png");
    matchOverChangeSheepRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchOverTextureAtlas, activity, "change_sheep.png");
    matchOverBackToMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchOverTextureAtlas, activity, "back_to_menu.png");
    matchOverExitRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(matchOverTextureAtlas, activity, "exit.png");

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
