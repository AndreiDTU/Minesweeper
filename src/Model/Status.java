package Model;

/**
 * Represents the status of a tile in a game of Minesweeper
 */
public enum Status {
    BOMB,
    FLAG_BOMB,
    FLAG_EMPTY,
    OPEN,
    CLOSED,
    BLOWN;

    public Status flag() {
        return switch (this) {
            case BOMB -> FLAG_BOMB;
            case CLOSED -> FLAG_EMPTY;
            case FLAG_EMPTY -> CLOSED;
            case FLAG_BOMB -> BOMB;
            default -> this;
        };
    }

    public boolean isFlag() {
        return this == FLAG_BOMB || this == FLAG_EMPTY;
    }

    public boolean isBomb() {
        return this == FLAG_BOMB || this == BOMB || this == BLOWN;
    }
}
