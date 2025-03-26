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

public class Cannon extends IterablePiece {

    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    );

    public Cannon(final Team team, final Position currentPosition) {
        super(team, currentPosition);
    }

    @Override
    protected void validatePath(final Pieces pieces, final Path path) {
        if (computeCountExistPieceExceptLastPosition(path, pieces) != 1) {
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

    private int computeCountExistPieceExceptLastPosition(final Path path, final Pieces pieces) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        if (!positions.isEmpty()) {
            positions.removeLast();
        }

        return (int) positions.stream()
                .filter(pieces::hasPiece)
                .count();
    }

    private boolean hasCannon(final Path path, final Pieces pieces) {
        return path.getPositions().stream()
                .filter(pieces::hasPiece)
                .anyMatch(position -> findCannonPiece(pieces, position));
    }

    private boolean findCannonPiece(final Pieces pieces, final Position position) {
        return pieces.getPieces().stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(piece -> piece.matchPieceType(PieceType.CANNON));
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }
}
