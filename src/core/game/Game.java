package core.game;

import static core.board.PieceColor.BLACK;
import static core.board.PieceColor.WHITE;



import core.board.Board;
import core.player.Player;

/** 控制玩游戏
 *  @author Jianliang Xu
 */
public class Game {

    /** 一个新的游戏，在棋盘board上玩 */
	public Game(Board board) {
        _board = board;
        _constBoard = _board.constantView();
    }
	/**
	 * 玩家black和white在棋盘board上开始一局新的游戏
	 * @param board 棋盘
	 * @param black 持黑子的一方玩家
	 * @param white 持白子的一方玩家
	 */
	public Game(Board board, Player black, Player white) {
		this(board);
		this.black = black;
		this.white = white;
		black.setColor(BLACK);
		white.setColor(WHITE);
		black.playGame(this);
		white.playGame(this);
	}

	/** 返回当前棋盘的只读视图 */
    public Board board() {
        return _constBoard;
    }

    /** 我的棋盘和棋盘的只读视图 */
    private Board _board, _constBoard;
    
    /**
     * 双方下棋的过程
     * @return 返回游戏的结果，要么白子胜，要么黑子胜，要么平局
     */
    public String process() {
    	Move currentMove = null;
    	_board.clear();
    	_board.draw();
    	int step = 0;
        while (true) {    
        	Move move = null;
        	if (_board.whoseMove() == BLACK) {
        		if (black.isManual()) System.out.print("black>");
        		move = black.findMove(currentMove);
        	} else {
        		if (white.isManual()) System.out.print("white>");
        		move = white.findMove(currentMove);
        	}
        	_board.makeMove(move);
        	_board.draw();
        	currentMove = move;
        	step++;
        	if(step>80) {
        		System.out.println("超过80步,平局");
        		return "tie";
        	}
        	if(_board.gameOver()) {
        		System.out.println(_board.whoseMove().opposite()+"胜利,游戏结束");
        		if(_board.whoseMove().opposite()==WHITE) {
        			return "whiteWin";
        		}else {
        			return "blackWin";
        		}
        	}
        }
       // _constBoard.draw();
    }
    
//    /**  recording the game into a file */
//    public void record(File gameRecord) {
//    	
//    	_board.record(gameRecord);
//    }
    /**
     * 持黑子的一方玩家
     */
    Player black = null;	
    /**
     * 持白子的一方玩家
     */
    Player white = null;    
}
