package Test;

import Model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void flagBomb() {
        assertEquals(Status.FLAG_BOMB, Status.BOMB.flag());
    }

    @Test
    void flagEmpty() {
        assertEquals(Status.FLAG_EMPTY, Status.CLOSED.flag());
    }

    @Test
    void unflagBomb() {
        assertEquals(Status.BOMB, Status.FLAG_BOMB.flag());
    }

    @Test
    void unflagEmpty() {
        assertEquals(Status.CLOSED, Status.FLAG_EMPTY.flag());
    }

    @Test
    void isFlag() {
        assertTrue(Status.FLAG_BOMB.isFlag() && Status.FLAG_EMPTY.isFlag());
    }

    @Test
    void isBomb() {
        assertTrue(Status.BOMB.isBomb() && Status.FLAG_BOMB.isBomb() && Status.BLOWN.isBomb());
    }
}