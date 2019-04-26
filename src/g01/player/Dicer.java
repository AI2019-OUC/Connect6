package g01.player;

import static core.board.PieceColor.*;
import static core.game.Move.*;

import java.util.Random;

import core.board.Board;

import core.game.Move;
import core.player.AI;

/* A player who plays by throwing dice*/
public class Dicer extends AI {

//    /** A new AI for GAME that will play MYCOLOR. */
//	public Dicer(Game game, PieceColor myColor) {
//        super(game, myColor, false);
//    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    @Override
    public Move findMove(Move opponentMove) {
    	Board b = board();
    	Random rand = new Random();
    	while (true) {
    		int index1 = rand.nextInt(SIDE * SIDE);
    		int index2 = rand.nextInt(SIDE * SIDE);
    		
    		if (index1 != index2 && b.get(index1) == EMPTY && b.get(index2) == EMPTY)
    			return new Move(index1, index2);
    	}
    }

	

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Dicer";
	}
}
