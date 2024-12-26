package Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Representation of the Minesweeper board
 */
public class Board {
    public static final int NUM_BOMBS = 10;

    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";

    LinkedHashMap<Coord, Status> board;

    /**
     * Generates a standard beginner game of Minesweeper with a 9x9 grid containing 10 bombs
     */
    public Board() {
        board = new LinkedHashMap<>();

        IntStream.range(0, 81)
                .mapToObj(Coord::fromInt)
                .forEach(e -> board.put(e, Status.CLOSED));

        new Random().ints(0, 81)
                .distinct()
                .limit(NUM_BOMBS)
                .mapToObj(Coord::fromInt)
                .forEach(e -> board.put(e, Status.BOMB));
    }

    /**
     * Get an ArrayList of the status of a given coordinate's neighbours
     * @param coord a Coord whose neighbours we want to access
     * @return an ArrayList of the statuses of the coordinate's neighbours' statuses
     */
    public ArrayList<Status> getNeighbours(Coord coord) {
        return coord.neighbours()
                .stream()
                .filter(board::containsKey)
                .map(board::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Reveal the tile at a given coordinate
     * @param coord a Coord of the tile to be revealed
     * @return a boolean indicating whether the move resulted in an explosion
     */
    public boolean reveal(Coord coord) {
        return switch (board.get(coord)) {
            case BOMB -> {
                board.replace(coord, Status.BLOWN);
                yield false;
            }
            case CLOSED -> {
                long neighbours = getNeighbours(coord).stream()
                        .filter(x -> x == Status.BOMB || x == Status.BLOWN || x == Status.FLAG_BOMB)
                        .count();

                board.replace(coord, Status.OPEN);

                if(neighbours == 0) {
                    coord.neighbours().forEach(e -> {
                        if (board.get(e) == Status.CLOSED) reveal(e);
                    });
                }

                yield true;
            }
            default -> true;
        };
    }

    /**
     * Flag the tile at a given coordinate
     * @param coord a Coord of the tile to be flagged
     * @return always returns true
     */
    public boolean flag(Coord coord) {
        board.replace(coord, board.get(coord).flag());
        return true;
    }

    /**
     * A method to check whether a game has been won
     * @return a boolean indicating whether the board contains anymore unrevealed tiles, or flags over unrevealed tiles
     */
    public boolean win() {
        return !board.containsValue(Status.CLOSED) && !board.containsValue(Status.FLAG_EMPTY);
    }

    /**
     * A method which returns how many unmarked bombs remain on the board
     * @return the number of remaining unmarked bombs as an int
     */
    public int bombs() {
        return (int)board.values().stream().filter(Status.BOMB::equals).count();
    }

    /**
     * A method which returns how many flags there are on the board, not taking into account whether the flags are misplaced
     * @return the number of flags on the board as an int
     */
    public int flags() {
        return (int)board.values().stream().filter(Status::isFlag).count();
    }

    /**
     * Formats the remaining bombs counter for display
     * @return a String representing formatted count of the number of bombs minus the number of flags
     */
    public String bombCounter() {
        return ANSI_RED + Math.max(NUM_BOMBS - flags(), 0) + ANSI_RESET;
    }

    /**
     * DEBUG: Testing method for getting the status of a certain tile directly
     * @param coord a Coord of the tile accessed
     * @return the status of the tile
     */
    public Status getStatus(Coord coord) {
        return board.get(coord);
    }

    /**
     * DEBUG: Testing method which instantly opens all closed tiles
     */
    public void instantWin() {
        IntStream.range(0, 81)
                .mapToObj(Coord::fromInt)
                .filter(e -> board.get(e) == Status.CLOSED)
                .forEach(e -> board.replace(e, Status.OPEN));
    }

    /**
     * DEBUG: Method which returns the entire board
     * @return a LinkedHashMap which contains all tiles in the game
     */
    public LinkedHashMap<Coord, Status> getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ANSI_YELLOW).append("  1 2 3 4 5 6 7 8 9\t").append(bombCounter()).append("\n");
        for(int i = 1; i <= 9; i++) {
            sb.append(ANSI_YELLOW).append((char)(i - 1 + 'a')).append(ANSI_RESET).append(" ");
            for(int j = 1; j <= 9; j++) {
                sb.append(getIcon(new Coord(i, j))).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Private method to get the icon of a given tile
     * @param coord coordinates of the tile
     * @return the Icon
     */
    private String getIcon(Coord coord) {
        return switch (board.get(coord)) {
            case CLOSED, BOMB           -> "#";
            case FLAG_BOMB, FLAG_EMPTY  -> ANSI_YELLOW + "F" + ANSI_RESET;
            case BLOWN                  -> ANSI_YELLOW + "X" + ANSI_RESET;

            case OPEN -> switch (
                (int)getNeighbours(coord).stream()
                    .filter(Status::isBomb)
                    .count()
            ) {
                case 0 -> " ";
                case 1 -> ANSI_BLUE   + 1 + ANSI_RESET;
                case 2 -> ANSI_GREEN  + 2 + ANSI_RESET;
                case 3 -> ANSI_RED    + 3 + ANSI_RESET;
                case 4 -> ANSI_PURPLE + 4 + ANSI_RESET;
                case 5 -> ANSI_RED    + 5 + ANSI_RESET;
                case 6 -> ANSI_CYAN   + 6 + ANSI_RESET;
                case 7 -> ANSI_PURPLE + 7 + ANSI_RESET;
                case 8 -> ANSI_GREEN  + 8 + ANSI_RESET;
                default -> "E"; // This shouldn't be possible
            };
        };
    }
}
