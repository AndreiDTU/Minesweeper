package Model;

/**
 * A record representing a move in Minesweeper
 * @param coord Coord object representing the coordinates which this move affects
 * @param flag a boolean representing whether this move is placing a flag or revealing a tile
 */
public record Move(Coord coord, boolean flag) {}
