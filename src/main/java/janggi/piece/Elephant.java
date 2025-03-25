package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.DOWN_LEFT;
import static janggi.piece.direction.Direction.DOWN_RIGHT;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;
import static janggi.piece.direction.Direction.UP_LEFT;
import static janggi.piece.direction.Direction.UP_RIGHT;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(DOWN, DOWN_RIGHT, DOWN_RIGHT),
            new Movement(DOWN, DOWN_LEFT, DOWN_LEFT),
            new Movement(UP, UP_RIGHT, UP_RIGHT),
            new Movement(UP, UP_LEFT, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT, DOWN_RIGHT),
            new Movement(LEFT, DOWN_LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT, UP_RIGHT),
            new Movement(LEFT, UP_LEFT, UP_LEFT)
    );

    public Elephant(final Team team) {
        super(PieceType.ELEPHANT, team);
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Movement> getMovements() {
        return MOVEMENTS;
    }
}
