package com.ralibi.dodombaan.manager;

public class GameConfigurationManager {

	public static final int[] STRENGTH 	= {3, 1, 1, 4, 2, 2};
	public static final int[] SPEED 	= {2, 3, 2, 1, 3, 4};
	public static final int[] AGILITY 	= {2, 3, 4, 2, 2, 1};
	
	public static final float[] DENSITY = {.5f, 1f, 3f, 10f, 40f, 100f}; // depend on strength
	public static final float[] FORCE 	= {40, 60, 100, 150, 300, 500}; // depend on speed

    
    private static final GameConfigurationManager INSTANCE = new GameConfigurationManager();
    
    public static GameConfigurationManager getInstance(){
        return INSTANCE;
    }
}
