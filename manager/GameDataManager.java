package com.ralibi.dodombaan.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GameDataManager {
  // CONSTANT
  private final String SFX_SETTING = "SFX_SETTING";
  private final String MUSIC_SETTING = "MUSIC_SETTING";
  private final String VIBRATION_SETTING = "VIBRATION_SETTING";
  private final String TOTAL_ROUND_SETTING = "TOTAL_ROUND_SETTING";
  
  
  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------
  private SharedPreferences sharedPreferences;
  private Editor editor;
  
  public int p1SheepIndex = 0;
  public int p2SheepIndex = 0;
  public int arenaIndex = 0;
  public int winner = 0;
  public int p1Score = 0;
  public int p2Score = 0;
  public int maxScore = 3;

  private static final GameDataManager INSTANCE = new GameDataManager();

  public static GameDataManager getInstance() {
    return INSTANCE;
  }
  
  private GameDataManager(){
    sharedPreferences = ResourcesManager.getInstance().activity.getPreferences(Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }

 

  public int getTotalRoundSetting() {
    return sharedPreferences.getInt(TOTAL_ROUND_SETTING, GameConfigurationManager.DEFAULT_TOTAL_ROUND_SETTING);
  }
  
  public int getTotalMaximumScore() {
    return (getTotalRoundSetting() + 1) / 2;
  }

  public void setTotalRoundSetting(int totalRoundSetting) {
    editor.putInt(TOTAL_ROUND_SETTING, totalRoundSetting);
    editor.commit();
  }

  public boolean isSfxSetting() {
    return sharedPreferences.getBoolean(SFX_SETTING, GameConfigurationManager.DEFAULT_SFX_SETTING);
  }

  public void setSfxSetting(boolean sfxSetting) {
    editor.putBoolean(SFX_SETTING, sfxSetting);
    editor.commit();
  }

  public boolean isMusicSetting() {
    return sharedPreferences.getBoolean(MUSIC_SETTING, GameConfigurationManager.DEFAULT_MUSIC_SETTING);
  }

  public void setMusicSetting(boolean musicSetting) {
    editor.putBoolean(MUSIC_SETTING, musicSetting);
    editor.commit();
  }

  public boolean isVibrationSetting() {
    return sharedPreferences.getBoolean(VIBRATION_SETTING, GameConfigurationManager.DEFAULT_VIBRATION_SETTING);
  }

  public void setVibrationSetting(boolean vibrationSetting) {
    editor.putBoolean(VIBRATION_SETTING, vibrationSetting);
    editor.commit();
  }

}
