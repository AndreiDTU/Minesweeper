package Test;

import Model.Coord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordTest {

    @Test
    void neighbours() {
        Coord coord = new Coord(3, 3);
        Coord[] neighbours = {
                new Coord(4, 3),
                new Coord(2, 3),
                new Coord(3, 4),
                new Coord(3, 2),
                new Coord(4, 4),
                new Coord(2, 4),
                new Coord(4, 2),
                new Coord(2, 2),
        };

        assertArrayEquals(coord.neighbours().toArray(), neighbours);
    }

    @Test
    void fromInt() {
        assertArrayEquals(
                new Coord[]{Coord.fromInt(0),    Coord.fromInt(80)},
                new Coord[]{new Coord(1, 1), new Coord(9, 9)}
        );
    }
}