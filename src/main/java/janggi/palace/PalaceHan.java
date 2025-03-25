package janggi.palace;

import janggi.board.Position;
import java.util.List;

public class PalaceHan extends Palace {

    public PalaceHan() {
        positions = List.of(
                new Position(3, 7),
                new Position(4, 7),
                new Position(5, 7),
                new Position(3, 8),
                new Position(4, 8),
                new Position(5, 8),
                new Position(3, 9),
                new Position(4, 9),
                new Position(5, 9)
        );
    }
}
