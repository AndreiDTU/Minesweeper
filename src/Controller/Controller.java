package Controller;

import Model.Board;
import Model.Move;

/**
 * Controller class which affects the Board externally
 */
public class Controller {
    /**
     * Takes a move and a board and makes the move on the board
     * @param move a Move object, the move to be made
     * @param board a Board object, the board on which the move is to be made
     * @return a boolean representing whether the move resulted in an explosion
     */
    public static boolean make(Move move, Board board) {
        board.incrementMoves();
        return move.flag()
                ? board.flag(move.coord())
                : board.reveal(move.coord());
    }
}
