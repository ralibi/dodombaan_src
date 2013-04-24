package com.ralibi.dodombaan.manager;

public class GameDataManager {
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------

	public int p1SheepIndex = 0;
	public int p2SheepIndex = 0;
	public int arenaIndex = 0;
	public int winner = 0;
	public int p1Score = 0;
	public int p2Score = 0;
	public int maxScore = 5;
	
    
    private static final GameDataManager INSTANCE = new GameDataManager();
    
    public static GameDataManager getInstance(){
        return INSTANCE;
    }
}
