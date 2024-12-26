package Test;

import Model.Board;
import Model.Coord;
import Model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    @BeforeEach
    void resetBoard() {
        board = new Board();
    }

    @RepeatedTest(5)
    void testBoard() {
        assertEquals(board.bombs(), Board.NUM_BOMBS);
    }

    @RepeatedTest(5)
    void getNeighbours() {
        Coord coord = new Coord(3, 3);

        assertArrayEquals(
                board.getNeighbours(coord)
                        .stream()
                        .sorted()
                        .toArray(),
                coord.neighbours()
                        .stream()
                        .map(board::getStatus)
                        .sorted()
                        .toArray()
        );
    }

    @RepeatedTest(50)
    void reveal() {
        Coord coord = new Coord(3, 3);
        Status status = board.getStatus(coord);
        Status futureStatus = switch (status) {
            case CLOSED -> Status.OPEN;
            case BOMB -> Status.BLOWN;
            default -> status;
        };
        board.reveal(coord);
        assertEquals(board.getStatus(coord), futureStatus);
    }

    @RepeatedTest(50)
    void flag() {
        Coord coord = new Coord(3, 3);
        if (new Random().ints(0,2).findFirst().orElse(0) == 1)
            board.flag(coord);
        Status status = board.getStatus(coord);
        Status futureStatus = switch (status) {
            case CLOSED -> Status.FLAG_EMPTY;
            case BOMB -> Status.FLAG_BOMB;
            case FLAG_BOMB -> Status.BOMB;
            case FLAG_EMPTY -> Status.CLOSED;
            default -> status;
        };
        board.flag(coord);
        assertEquals(board.getStatus(coord), futureStatus);
    }

    @Test
    void lose() {
        assertFalse(board.win());
    }

    @Test
    void win() {
        board.instantWin();
        assertTrue(board.win());
    }

    @Test
    void bombs() {
        assertEquals((int)board.getBoard().values().stream().filter(Status.BOMB::equals).count(), board.bombs());
    }

    @Test
    void flags() {
        assertEquals((int)board.getBoard().values().stream().filter(Status::isFlag).count(), board.flags());
    }
}