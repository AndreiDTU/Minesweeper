package Model;

import java.util.ArrayList;

/**
 * 2D coordinate record
 * @param x int X-coordinate
 * @param y int Y-coordinate
 */
public record Coord(int x, int y) {
    /**
     * Returns this object's neighbouring coordinates
     * @return an ArrayList containing the neighbouring coordinates
     */
    public ArrayList<Coord> neighbours() {
        ArrayList<Coord> neighbours = new ArrayList<>();
        neighbours.add(new Coord(x + 1, y));
        neighbours.add(new Coord(x - 1, y));
        neighbours.add(new Coord(x, y + 1));
        neighbours.add(new Coord(x, y - 1));
        neighbours.add(new Coord(x + 1, y + 1));
        neighbours.add(new Coord(x - 1, y + 1));
        neighbours.add(new Coord(x + 1, y - 1));
        neighbours.add(new Coord(x - 1, y - 1));
        return neighbours;
    }

    /**
     * Turns a number from 0 to 80 into a valid coord for a Minesweeper board
     * @param i an int expected to be from 0 to 80
     * @return a coord corresponding to this int
     */
    public static Coord fromInt(int i) {
        return new Coord(i / 9 + 1, i % 9 + 1);
    }
}
