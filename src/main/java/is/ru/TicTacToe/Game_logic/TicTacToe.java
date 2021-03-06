package is.ru.TicTacToe.Game_logic;

import is.ru.TicTacToe.Game_exceptions.*;
import is.ru.TicTacToe.Game_domain.*;
import is.ru.TicTacToe.Game_logic.GameState;

public class TicTacToe {

    private Player player1, player2, currentPlayer, winner;
    public Board board;
    public GameState gameState;
    private boolean isOver;

    /**
    *
    *
    */
    public TicTacToe(){
        board = new Board();
        gameState = gameState.GAME_RUNNING;
        player1 = new Player("Player1", PlayerSymbol.X);
        player2 = new Player("Player2", PlayerSymbol.O);
        currentPlayer = player1;
        isOver = false;
    }

    /**
    * Changes current player
    */
    public void changePlayer(){
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

	public GameState getGameState(){
        return gameState;
    }

    /**
    * Marks move if game is not over
    * @param move selected location for marker
    * <return> returns marker
    */
    public char makeMove(int move) throws AlreadyOccupiedException,
                                          BoundaryException,
                                          IllegalSymbolException{

        checkForWinner();                       
        Coordinates coord = DimensionMapper.getCoordinate(move);
        if(isOver){

            return board.get(coord.getRow(), coord.getColumn());
        }
        if(board.isBoardFull()){
            gameState = gameState.GAME_TIE;
        }
        board.set(coord.getRow(), coord.getColumn(), currentPlayer.getMarker());
        return currentPlayer.getMarker();
    }

    /**
    *
    * @param value The location where we want to get the value from
    * <return> returns char value of location
    */
    public char getBoardValue(int value) throws BoundaryException {
        Coordinates coord = DimensionMapper.getCoordinate(value);
        return board.get(coord.getRow(), coord.getColumn());
    }

    /**
    * <return> returns current player
    */
    public String getCurrentPlayerName(){
        return currentPlayer.getName();
    }

    /**
    *
    * <return> returns
    */
    public Player getWinner(){
        
        return winner;
    }

    public boolean checkForWinner() throws BoundaryException{
        if(isOver){ return true; }
        if(!checkDiagonal()){
            if(!checkHorizontal()){
                if(!checkVertical()){
                    return false;
                }
            }
        }

        isOver = true;
        gameState = gameState.GAME_WINNER;
        winner = currentPlayer;
        return true;
    }

    /**
    *
    *
    */
    private boolean checkHorizontal() throws BoundaryException{
        for(int i = 0; i < 3; i++){
            if(board.get(i, 0) == board.get(i, 1) && board.get(i, 0) == board.get(i, 2) && board.get(i, 0) != PlayerSymbol.NONE){
                return true;
            }
        }
        return false;
    }

    /**
    *
    *
    */
    private boolean checkVertical() throws BoundaryException{
        for(int i = 0; i < 3; i++){
            if(board.get(0, i) == board.get(1, i) && board.get(0, i) == board.get(2, i) && board.get(0, i) != PlayerSymbol.NONE){
                return true;
            }
        }
        return false;
    }

    /**
    *
    *
    */
    private boolean checkDiagonal() throws BoundaryException{
        if(board.get(0, 0) == board.get(1, 1) && board.get(1, 1) == board.get(2, 2) && board.get(0, 0) != PlayerSymbol.NONE) {
            return true;
        }
        if(board.get(0, 2) == board.get(1, 1) && board.get(1, 1) == board.get(2, 0) && board.get(0, 2) != PlayerSymbol.NONE){
            return true;
        }
        return false;
    }
}
