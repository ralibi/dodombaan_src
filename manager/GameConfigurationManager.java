package com.ralibi.dodombaan.manager;

public class GameConfigurationManager {

  public static final int[] STRENGTH = { 3, 1, 1, 4, 2, 2 };
  public static final int[] SPEED = { 2, 3, 2, 1, 3, 4 };
  public static final int[] AGILITY = { 2, 3, 4, 2, 2, 1 };

  public static final float[] DENSITY = { .5f, 1f, 3f, 10f, 40f, 100f }; // depend on strength
  public static final float[] FORCE = { 30, 50, 80, 180, 300, 500 }; // depend on speed
  public static final float[] ANGLE = { 8, 18, 30, 46, 62, 80 }; // depend on speed

  public static final int[] DAMPING = { 10, 5, 3 }; // depend on speed

  public static final int VIBRATE_STRENGTH = 25;

  public static final boolean DEFAULT_SFX_SETTING = true;
  public static final boolean DEFAULT_MUSIC_SETTING = true;
  public static final boolean DEFAULT_VIBRATION_SETTING = true;
  public static final int DEFAULT_TOTAL_ROUND_SETTING = 5;

  private static final GameConfigurationManager INSTANCE = new GameConfigurationManager();

  public static GameConfigurationManager getInstance() {
    return INSTANCE;
  }
}
