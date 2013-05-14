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

  // public String fontName = "OpenSans-Regular.ttf";
  public String fontName = "AlfaSlabOne-Regular-OTF.otf";
  public String fontIconName = "fontawesome-webfont.ttf";
  // public String fontScoreName = "OpenSans-Regular.ttf";
  public String fontScoreName = "AlfaSlabOne-Regular-OTF.otf";

  // GENERAL
  private final int sheepCount = 6;
  private final int arenaCount = 3;

  // ---------------------------------------------
  // TEXTURES & TEXTURE REGIONS
  // ---------------------------------------------

  // Splash Scene
  // public ITextureRegion splashBackgroundRegion;
  private BuildableBitmapTextureAtlas splashTextureAtlas;

  // Menu Scene
  // public ITextureRegion menuBackgroundRegion;

  // public ITextureRegion multiplayerOverBluetoothRegion;

  private BuildableBitmapTextureAtlas menuTextureAtlas;

  // SheepSelectionScene
  // public ITextureRegion sheepSelectionBackgroundRegion;
  public ITextureRegion sheepScrollBackgroundRegion;
  // public ITextureRegion sheepSelectionNextRegion;
  // public ITextureRegion sheepSelectionBackRegion;

  public ITextureRegion powerBlankRegion;
  public ITextureRegion powerFilledRegion;

  public ITextureRegion strengthThumbRegion;
  public ITextureRegion speedThumbRegion;
  public ITextureRegion agilityThumbRegion;
  
  
  public List<ITextureRegion> sheepSelectionSheepRegions = new ArrayList<ITextureRegion>();
  private BuildableBitmapTextureAtlas sheepSelectionTextureAtlas;

  // MatchSettingsScene
  // public ITextureRegion matchSettingsBackgroundRegion;
  public ITextureRegion arenaScrollBackgroundRegion;
  // public ITextureRegion matchSettingsNextRegion;
  // public ITextureRegion matchSettingsBackRegion;
  public List<ITextureRegion> matchSettingsArenaRegions = new ArrayList<ITextureRegion>();
  private BuildableBitmapTextureAtlas matchSettingsTextureAtlas;

  // GamePlayScene
  public ITextureRegion gamePlayBackgroundRegion;
  public ITextureRegion overlayRegion;
  public ITextureRegion pauseOverlayRegion;


  // public ITextureRegion gamePlayNextRoundRegion;

  public List<ITextureRegion> gamePlaySheepSegment1Regions = new ArrayList<ITextureRegion>();
  public List<ITextureRegion> gamePlaySheepSegment2Regions = new ArrayList<ITextureRegion>();
  public List<ITextureRegion> gamePlaySheepSegment3Regions = new ArrayList<ITextureRegion>();

  public List<ITextureRegion> gamePlayPlayingArenaRegions = new ArrayList<ITextureRegion>();
  public List<TiledTextureRegion> nailNormalRegions = new ArrayList<TiledTextureRegion>();
  public ITextureRegion gamePlayIndicatorRegion;
  public ITextureRegion gamePlayIndicatorBackgroundRegion;
  public ITextureRegion gamePlayNailRegion;

  public ITextureRegion scoreboardRegionRegion;
  public ITextureRegion scoreBackgroundRegionRegion;
  
  private BuildableBitmapTextureAtlas gamePlayTextureAtlas;

  // MatchOverScene
  // public ITextureRegion matchOverBackgroundRegion;
  
  private BuildableBitmapTextureAtlas matchOverTextureAtlas;

  
  // SettingsScene
  // public ITextureRegion settingsBackgroundRegion;
  public List<ITextureRegion> totalScrollRegions = new ArrayList<ITextureRegion>();
  
  private BuildableBitmapTextureAtlas settingsTextureAtlas;

  
  // SHARED RESOURCE
  public ITextureRegion baseBackgroundRegion;

  public ITextureRegion start2PlayerNormalRegion;
  public ITextureRegion start2PlayerPressedRegion;
  public ITextureRegion start2PlayerDisabledRegion;
  public ITextureRegion settingsNormalRegion;
  public ITextureRegion settingsPressedRegion;
  public ITextureRegion settingsDisabledRegion;
  
  public ITextureRegion navLeftNormalRegion;
  public ITextureRegion navLeftPressedRegion;
  public ITextureRegion navLeftDisabledRegion;
  public ITextureRegion navRightNormalRegion;
  public ITextureRegion navRightPressedRegion;
  public ITextureRegion navRightDisabledRegion;

  public ITextureRegion selectNormalRegion;
  public ITextureRegion selectPressedRegion;
  public ITextureRegion selectDisabledRegion;

  public ITextureRegion nextNormalRegion;
  public ITextureRegion nextPressedRegion;
  public ITextureRegion nextDisabledRegion;

  public ITextureRegion backNormalRegion;
  public ITextureRegion backPressedRegion;
  public ITextureRegion backDisabledRegion;

  public ITextureRegion yesNormalRegion;
  public ITextureRegion yesPressedRegion;
  public ITextureRegion yesDisabledRegion;
  
  public ITextureRegion noNormalRegion;
  public ITextureRegion noPressedRegion;
  public ITextureRegion noDisabledRegion;
  
  public ITextureRegion doneNormalRegion;
  public ITextureRegion donePressedRegion;
  public ITextureRegion doneDisabledRegion;
  
  public ITextureRegion gamePlayPauseNormalRegion;
  public ITextureRegion gamePlayPausePressedRegion;
  public ITextureRegion gamePlayPauseDisabledRegion;
  public ITextureRegion gamePlayResumeNormalRegion;
  public ITextureRegion gamePlayResumePressedRegion;
  public ITextureRegion gamePlayResumeDisabledRegion;
  public ITextureRegion gamePlayExitToMenuNormalRegion;
  public ITextureRegion gamePlayExitToMenuPressedRegion;
  public ITextureRegion gamePlayExitToMenuDisabledRegion;

  public ITextureRegion matchOverRematchNormalRegion;
  public ITextureRegion matchOverRematchPressedRegion;
  public ITextureRegion matchOverRematchDisabledRegion;
  public ITextureRegion matchOverChangeSheepNormalRegion;
  public ITextureRegion matchOverChangeSheepPressedRegion;
  public ITextureRegion matchOverChangeSheepDisabledRegion;
  public ITextureRegion matchOverBackToMenuNormalRegion;
  public ITextureRegion matchOverBackToMenuPressedRegion;
  public ITextureRegion matchOverBackToMenuDisabledRegion;
  public ITextureRegion matchOverExitNormalRegion;
  public ITextureRegion matchOverExitPressedRegion;
  public ITextureRegion matchOverExitDisabledRegion;
  
  public ITextureRegion logoImageRegion;
  public ITextureRegion logoTextRegion;
  public ITextureRegion leftLeafRegion;
  public ITextureRegion rightLeafRegion;
  public ITextureRegion grassRegion;
  

  public ITextureRegion comboboxRegion;
  public ITextureRegion checkboxUncheckedRegion;
  public ITextureRegion checkboxCheckedRegion;

  public ITextureRegion largePopupBackgroundRegion;

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
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    splashTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

    baseBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "shared/wood_80_background.png");
    leftLeafRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "shared/left_leaf.png");
    rightLeafRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "shared/right_leaf.png");
    logoImageRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "shared/logo_image.png");
    logoTextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "shared/logo_text.png");
    grassRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "shared/grass.png");

    textureAtlasBuilderException(this.splashTextureAtlas);
  }

  public void unloadSplashScreen() {
    // splashTextureAtlas.unload();
    // splashBackgroundRegion = null;
  }


  // Shared Resources
  // ###########################################
  public void loadSharedResources() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/shared/");
    sharedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

    start2PlayerNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/2_players.png");
    start2PlayerPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/2_players.png");
    start2PlayerDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/2_players.png");
    
    settingsNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/settings.png");
    settingsPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/settings.png");
    settingsDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/settings.png");
    
    // Navigation
    navLeftNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/nav_left.png");
    navLeftPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/nav_left.png");
    navLeftDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/nav_left.png");
    navRightNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/nav_right.png");
    navRightPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/nav_right.png");
    navRightDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/nav_right.png");

    selectNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/select.png");
    selectPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/select.png");
    selectDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/select.png");
    
    nextNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/next.png");
    nextPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/next.png");
    nextDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/next.png");

    backNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/back.png");
    backPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/back.png");
    backDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/back.png");
    
    yesNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/yes.png");
    yesPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/yes.png");
    yesDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/yes.png");

    noNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/no.png");
    noPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/no.png");
    noDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/no.png");

    doneNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/ok.png");
    donePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/ok.png");
    doneDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/ok.png");

    // Gameplay button
    gamePlayPauseNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/pause.png");
    gamePlayPausePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/pause.png");
    gamePlayPauseDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/pause.png");
    
    gamePlayResumeNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/resume.png");
    gamePlayResumePressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/resume.png");
    gamePlayResumeDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/resume.png");
    
    gamePlayExitToMenuNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/quit.png");
    gamePlayExitToMenuPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/quit.png");
    gamePlayExitToMenuDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/quit.png");

    // mathover button
    matchOverRematchNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/rematch.png");
    matchOverRematchPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/rematch.png");
    matchOverRematchDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/rematch.png");
    
    matchOverChangeSheepNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/change_sheep.png");
    matchOverChangeSheepPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/change_sheep.png");
    matchOverChangeSheepDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/change_sheep.png");
    
    matchOverBackToMenuNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/main_menu.png");
    matchOverBackToMenuPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/main_menu.png");
    matchOverBackToMenuDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/main_menu.png");
    
    matchOverExitNormalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "normal/quit.png");
    matchOverExitPressedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "pressed/quit.png");
    matchOverExitDisabledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "disabled/quit.png");
    
    comboboxRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "combobox.png");
    checkboxCheckedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "checkbox_checked.png");
    checkboxUncheckedRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "checkbox_unchecked.png");
    
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    largePopupBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sharedTextureAtlas, activity, "match_settings/arena_scroll_background.png");

    textureAtlasBuilderException(this.sharedTextureAtlas);
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
    fontSmall = FontFactory.createFromAsset(activity.getFontManager(), mainFontSmallTexture, activity.getAssets(), fontName, (float) 16, true, Color.BLACK.getABGRPackedInt());
    fontSmall.load();

    final ITexture fontScoreIconTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    fontScore = FontFactory.createFromAsset(activity.getFontManager(), fontScoreIconTexture, activity.getAssets(), fontScoreName, (float) 36, true, Color.WHITE.getABGRPackedInt());
    fontScore.load();
  }

  
  
  
  
  // MainMenu Methods
  // ###########################################

  public void loadMenuResources() {
    loadSharedResources();
    loadSharedFonts();
    loadMenuGraphics();
    loadMenuAudio();
  }
  private void loadMenuGraphics() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    // multiplayerOverBluetoothRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu/multiplayer_over_bluetooth.png");
    textureAtlasBuilderException(this.menuTextureAtlas);
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
  // ###########################################
  public void loadSettingsResources() {
    loadSettingsGraphics();
  }

  private void loadSettingsGraphics() {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    settingsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

    totalScrollRegions.clear();
    for (int i = 0; i < 9; i++) {
      totalScrollRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsTextureAtlas, activity, "shared/total_round/total_round (" + (i + 1) + ").png"));
    }
    
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
    sheepSelectionTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);

    sheepScrollBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/sheep_scroll_background.png");

    powerBlankRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/power_blank.png");
    powerFilledRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/power_filled.png");

    strengthThumbRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/strength_thumb.png");
    speedThumbRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/speed_thumb.png");
    agilityThumbRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/agility_thumb.png");

    sheepSelectionSheepRegions.clear();
    for (int i = 0; i < sheepCount; i++) {
      sheepSelectionSheepRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheepSelectionTextureAtlas, activity, "sheep_selection/sheeps/sheep (" + (i + 1) + ").png"));
    }

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

    gamePlayIndicatorRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "indicator.png");
    gamePlayIndicatorBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "indicator_background.png");
    scoreboardRegionRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "scoreboard.png");
    scoreBackgroundRegionRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "score_background.png");

    gamePlayIndicatorBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "indicator_background.png");

    gamePlayNailRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "nail.png");

    // gamePlaySheepSegmentRegions.clear();
    gamePlaySheepSegment1Regions.clear();
    gamePlaySheepSegment2Regions.clear();
    gamePlaySheepSegment3Regions.clear();
    for (int i = 0; i < sheepCount; i++) {
      // gamePlaySheepSegmentRegions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas,
      // activity, "segments/segment (" + (i + 1) + ").png"));
      gamePlaySheepSegment1Regions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segments/segment (" + (i + 1) + ") (1).png"));
      gamePlaySheepSegment2Regions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segments/segment (" + (i + 1) + ") (2).png"));
      gamePlaySheepSegment3Regions.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "segments/segment (" + (i + 1) + ") (3).png"));
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

    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    overlayRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayTextureAtlas, activity, "shared/overlay_background.png");
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
