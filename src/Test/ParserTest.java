package Test;

import Controller.Parser;
import Model.Coord;
import Model.Move;
import Model.MoveType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void parseReveal() {
        assertEquals(Parser.parseMove("a1"), new Move(new Coord(1, 1), MoveType.REVEAL));
    }
    @Test
    void parseFlag() {
        assertEquals(Parser.parseMove("a1f"), new Move(new Coord(1, 1), MoveType.FLAG));
    }
    @Test
    void parseChord() {
        assertEquals(Parser.parseMove("a1c"), new Move(new Coord(1, 1), MoveType.CHORD));
    }

    @Test
    void validateReveal() {
        assertTrue(Parser.validateMove("a1"));
    }

    @Test
    void validateFlag() {
        assertTrue(Parser.validateMove("a1f"));
    }

    @Test
    void validateChord() {
        assertTrue(Parser.validateMove("a1c"));
    }
}