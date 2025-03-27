package domain.castle;

import domain.Position;
import java.util.List;

public class Castle {
    public List<Position> castle;

    public Castle() {
        castle = List.of(
                new Position(1, 4), new Position(1, 5), new Position(1, 6),
                new Position(2, 4), new Position(2, 5), new Position(2, 6),
                new Position(3, 4), new Position(3, 5), new Position(3, 6),
                new Position(8, 4), new Position(8, 5), new Position(8, 6),
                new Position(9, 4), new Position(9, 5), new Position(9, 6),
                new Position(10, 4), new Position(10, 5), new Position(10, 6)
        );
    }


}
