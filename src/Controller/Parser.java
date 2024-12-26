package Controller;

import Model.Coord;
import Model.Move;
import Model.MoveType;

/**
 * Static method container class for methods related to parsing and validating user input
 */
public class Parser {
    /**
     * Parses a piece of text into a Move object, expects its argument to have already been validated
     * @param move a String representing a move of Minesweeper
     * @return a Move object representing the same move
     */
    public static Move parseMove(String move) {
        move = move.toLowerCase().trim();
        Coord coord = new Coord(move.charAt(0) - 'a' + 1, move.charAt(1) - '0');
        return move.length() >= 3
                ? move.charAt(2) == 'f'
                ? new Move(coord, MoveType.FLAG)
                : new Move(coord, MoveType.CHORD)
                : new Move(coord, MoveType.REVEAL);
    }

    /**
     * Validates a piece of text as a Minesweeper move
     * @param move a String to be validated
     * @return a boolean representing whether the argument is a valid move
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean validateMove(String move) {
        return move.toLowerCase().trim().matches("[a-i][1-9][cf]?");
    }
}
