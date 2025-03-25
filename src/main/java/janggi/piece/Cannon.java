package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    );

    public Cannon(final Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (computeCountExistPieceExceptLast(path, pieces) != 1) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasCannon(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    @Override
    protected List<Movement> getMovements() {
        return MOVEMENTS;
    }

    private int computeCountExistPieceExceptLast(final Path path, final Map<Position, Piece> pieces) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();

        return (int) positions.stream()
                .filter(pieces::containsKey)
                .count();
    }

    private boolean hasCannon(final Path path, final Map<Position, Piece> pieces) {
        return path.getPositions().stream()
                .filter(pieces::containsKey)
                .map(pieces::get)
                .anyMatch(piece -> piece.matchPieceType(PieceType.CANNON));
    }
}
