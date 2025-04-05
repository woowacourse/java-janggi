package janggi.domain.piece.direction;

import janggi.domain.piece.position.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum PalaceMovement {

    HAN_MIDDLE(new Position(2, 5),
            new Movements(List.of(
                    new Movement(Direction.UP_RIGHT),
                    new Movement(Direction.UP_LEFT),
                    new Movement(Direction.DOWN_RIGHT),
                    new Movement(Direction.DOWN_LEFT)))
    ),

    HAN_TOP_LEFT(new Position(1, 4),
            new Movements(List.of(
                    new Movement(Direction.DOWN_RIGHT))
            )),

    HAN_TOP_RIGHT(new Position(1, 6),
            new Movements(List.of(
                    new Movement(Direction.DOWN_LEFT))
            )),

    HAN_BOTTOM_LEFT(new Position(3, 4),
            new Movements(List.of(
                    new Movement(Direction.UP_RIGHT))
            )),

    HAN_BOTTOM_RIGHT(new Position(3, 6),
            new Movements(List.of(
                    new Movement(Direction.UP_LEFT))
            )),

    CHO_MIDDLE(new Position(9, 5),
            new Movements(List.of(
                    new Movement(Direction.UP_RIGHT),
                    new Movement(Direction.UP_LEFT),
                    new Movement(Direction.DOWN_RIGHT),
                    new Movement(Direction.DOWN_LEFT)))
    ),

    CHO_TOP_LEFT(new Position(8, 4),
            new Movements(List.of(
                    new Movement(Direction.DOWN_RIGHT))
            )),

    CHO_TOP_RIGHT(new Position(8, 6),
            new Movements(List.of(
                    new Movement(Direction.DOWN_LEFT))
            )),

    CHO_BOTTOM_LEFT(new Position(10, 4),
            new Movements(List.of(
                    new Movement(Direction.UP_RIGHT))
            )),

    CHO_BOTTOM_RIGHT(new Position(10, 6),
            new Movements(List.of(
                    new Movement(Direction.UP_LEFT))
            ));

    private final Position position;
    private final Movements movements;

    PalaceMovement(final Position position, final Movements movements) {
        this.position = position;
        this.movements = movements;
    }

    public static Optional<Movements> getMovements(final Position startPosition) {
        return Arrays.stream(values())
                .filter(palaceMovement -> palaceMovement.position.equals(startPosition))
                .map(PalaceMovement::getMovements)
                .findAny();
    }

    public Movements getMovements() {
        return movements;
    }
}
