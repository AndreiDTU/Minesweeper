package Model;

/**
 * A record representing a move in Minesweeper
 * @param coord Coord object representing the coordinates which this move affects
 * @param type represents whether this move is placing a flag, revealing a tile or a chord on a tile
 */
public record Move(Coord coord, MoveType type) {}
