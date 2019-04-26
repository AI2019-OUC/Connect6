package core.runner;

import core.game.GameEvent;

public class Main {

    public static void main(String[] args) {

    	GameEvent oucChampion = new GameEvent();
    	
    	//oucChampion.addPlayer(new core.player.Dicer());

    	oucChampion.addPlayer(new g01.player.Dicer());

    	oucChampion.addPlayer(new g02.player.Lucker());
    	
    	oucChampion.arrangeMatches(1000);
    	
    	oucChampion.run();
    }
}
