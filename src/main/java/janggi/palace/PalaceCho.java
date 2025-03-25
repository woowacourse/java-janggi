package janggi.palace;

import janggi.board.Position;
import java.util.List;

public class PalaceCho extends Palace {

    public PalaceCho() {
        positions = List.of(
                new Position(3, 0),
                new Position(4, 0),
                new Position(5, 0),
                new Position(3, 1),
                new Position(4, 1),
                new Position(5, 1),
                new Position(3, 2),
                new Position(4, 2),
                new Position(5, 2)
        );
    }
}
