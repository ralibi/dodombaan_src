package com.ralibi.dodombaan.manager;

public class GameDataManager {
  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------

  public int p1SheepIndex = 0;
  public int p2SheepIndex = 0;
  public int arenaIndex = 0;
  public int winner = 0;
  public int p1Score = 0;
  public int p2Score = 0;
  public int maxScore = 3;

  public int totalRoundSetting = GameConfigurationManager.DEFAULT_TOTAL_ROUND_SETTING;
  public boolean sfxSetting = GameConfigurationManager.DEFAULT_SFX_SETTING;
  public boolean musicSetting = GameConfigurationManager.DEFAULT_MUSIC_SETTING;
  public boolean vibrationSetting = GameConfigurationManager.DEFAULT_VIBRATION_SETTING;
  
  private static final GameDataManager INSTANCE = new GameDataManager();

  public static GameDataManager getInstance() {
    return INSTANCE;
  }
}
