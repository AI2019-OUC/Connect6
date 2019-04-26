package core.board;

import static core.board.PieceColor.*;
import static core.game.Move.*;


import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

import core.game.Move;


@SuppressWarnings("all")
public class Board extends Observable {

    
    public Board() {
        _board = new PieceColor[SIDE * SIDE];
        clear();
    }

   
    public Board(Board b) {
        _board = new PieceColor[SIDE * SIDE];
        internalCopy(b);
    }

    
    public Board constantView() {
        return this.new ConstantBoard();
    }

    
    public void clear() {
        _whoseMove = WHITE;
        _gameOver = false;

        for (int i = 0; i < SIDE * SIDE; i++) {
            _board[i] = EMPTY;
        }
       
        _board[Move.index('J', 'J')] = BLACK;
        moveList.clear();
        setChanged();
        notifyObservers();
    }

    
    public void copy(Board b) {
        internalCopy(b);
    }

   
    private void internalCopy(Board b) {
        _gameOver = b.gameOver();
        _whoseMove = b.whoseMove();
        //System.arraycopy(b._board, 0, _board, 0, SIDE * SIDE);
        for (int i = 0; i < SIDE * SIDE; i++) {
            _board[i] = b.get(i);
        }
        //System.arraycopy(b.moveList, 0, moveList, 0, b.moveList.size());
    }

    
    public boolean gameOver() {
        return _gameOver;
    }

    
    public PieceColor get(char c, char r) {
        assert validSquare(c, r);
        return _board[index(c, r)];
    }

    
    public PieceColor get(int k) {
        assert validSquare(k);
        return _board[k];
    }

    
    private void set(char c, char r, PieceColor v) {
        assert validSquare(c, r);
        set(index(c, r), v);
    }

    
    private void set(int k, PieceColor v) {
        assert validSquare(k);
        _board[k] = v;
    }

    
    public boolean legalMove(Move mov) {
        return true; // FIXME
    }

    
    public PieceColor whoseMove() {
        return _whoseMove;
    }

    
    public void makeMove(char c0, char r0, char c1, char r1) {
        makeMove(new Move(c0, r0, c1, r1));
    }

    
public boolean isWin(char c, char r, PieceColor pieceColor) {
        int count = 1;      
        int posX = 0;    
        int posY = 0;
        
        for(posX = c - 1; posX >='A'; posX--) {
            if (_board[Move.index((char)posX, r)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }   
        for(posX = c + 1; posX <='S'; posX++) {
            if (_board[Move.index((char)posX, r)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }
        
        count=1;
        for(posY = r - 1; posY >='A'; posY--) {
            if (_board[Move.index(c, (char)posY)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }
        for(posY = r + 1; posY <= 'S'; posY++) {
            if (_board[index(c, (char)posY)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }
        
        count=1;
        for(posX = c - 1, posY = r - 1; posX >='A' && posY >='A'; posX--, posY--) {
            if (_board[Move.index((char)posX, (char)posY)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }
        for(posX = c + 1, posY = r + 1; posX <= 'S' && posY <= 'S'; posX++, posY++) {
            if (_board[Move.index((char)posX, (char)posY)] == pieceColor) {
                count++;
                if (count >= 6) {
                    //count = 1;
                    return true;
                }
            }else {
                break;
            }
        }
        
        count=1;
        for(posX = c + 1, posY = r - 1; posX <= 'S' && posY >='A'; posX++, posY--) {
            if (_board[Move.index((char)posX, (char)posY)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }
        for(posX = c - 1, posY = r + 1; posX >='A' && posY <= 'S'; posX--, posY++) {
            if (_board[Move.index((char)posX, (char)posY)] == pieceColor) {
                count++;
                if (count >= 6) {
                    return true;
                }
            }else {
                break;
            }
        }
        return false;
    }


    
    public void makeMove(Move mov) {
        assert legalMove(mov);
        
        moveList.add(mov);
        System.out.println(_whoseMove + ": " + "move " + mov.toString());
        set(mov.col0(), mov.row0(), _whoseMove);
        _gameOver=isWin(mov.col0(), mov.row0(), _whoseMove);
        if(_gameOver==true) {
        	_whoseMove = _whoseMove.opposite();
        	setChanged();
            notifyObservers();
            return;
        }

        
        set(mov.col1(), mov.row1(), _whoseMove);
        _gameOver=isWin(mov.col1(), mov.row1(), _whoseMove);
        
        _whoseMove = _whoseMove.opposite();
        setChanged();
        notifyObservers();

        
    }

    
    public void undo() {
        Move mov = moveList.remove(moveList.size() - 1);
        undo(mov);
        _whoseMove = _whoseMove.opposite();
        setChanged();
        notifyObservers();
    }
  
    public void undo(Move mov) {
        set(mov.col0(), mov.row0(), EMPTY);
        set(mov.col1(), mov.row1(), EMPTY);
    }

    @Override
    public String toString() {
        return toString(false);
    }

   
    public String toString(boolean legend) {
    	StringBuffer strBuff = new StringBuffer();
    	
    	strBuff.append("  ");
    	for (int i = 0; i < SIDE; i++)
    		strBuff.append((char)('A' + i));
    	strBuff.append("\n");
    	
    	for (int i = 0; i < SIDE * SIDE; i++) {
    		if (i % SIDE == 0) 
    			strBuff.append((char)('A' + i / SIDE) + " ");
    		if (_board[i] == EMPTY) {
    			strBuff.append("-");
    		}
    		else if (_board[i] == BLACK) {
    			strBuff.append("x");
    		}
    		else {
    			strBuff.append("o");
    		}
    		if ((i+1) % SIDE == 0)
    			strBuff.append("\n");
    	}
        return strBuff.toString();  // FIXME
    }
    
    public void draw() {
    	System.out.print(this.toString(true));
    }
    
//    /** Recording the movelist into a file*/
//    public void record(File record) {
//    	
//    }

    
    private PieceColor _whoseMove;

   
    private PieceColor[] _board;

    
    private boolean _gameOver;

    
    static final PieceColor[] PIECE_VALUES = PieceColor.values();
   
    private ArrayList<Move> moveList = new ArrayList<>();

    @Override
    public boolean equals(Object b) {
        if (((Board) b)._whoseMove != _whoseMove || ((Board) b)._gameOver != _gameOver) {
            return false;
        }
        for (int i = 0; i < SIDE * SIDE; i++){
            if (((Board) b)._board[i] != _board[i]) {
                return false;
            }
        }
        return true;
    }

    
    private class ConstantBoard extends Board implements Observer {
        
        ConstantBoard() {
            super(Board.this);
            Board.this.addObserver(this);
        }

        @Override
        public void copy(Board b) {
            assert false;
        }

        @Override
        public void clear() {
            assert false;
        }

        @Override
        public void makeMove(Move move) {
            assert false;
        }

        
        @Override
        public void undo() {
            assert false;
        }

        @Override
        public void update(Observable obs, Object arg) {
            super.copy((Board) obs);
            setChanged();
            notifyObservers(arg);
        }
    }
}
