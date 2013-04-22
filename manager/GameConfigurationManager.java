package com.ralibi.dodombaan.manager;

public class GameConfigurationManager {

	public int[] strength 	= {3, 1, 1, 4, 2, 2};
	public int[] speed 		= {2, 3, 2, 1, 3, 4};
	public int[] agility 	= {2, 3, 4, 2, 2, 1};

    
    private static final GameConfigurationManager INSTANCE = new GameConfigurationManager();
    
    public static GameConfigurationManager getInstance(){
        return INSTANCE;
    }
}
