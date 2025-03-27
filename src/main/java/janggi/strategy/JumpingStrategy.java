package janggi.strategy;

import janggi.direction.Movement;
import janggi.direction.PieceMovement;
import janggi.piece.Piece;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JumpingStrategy implements MoveStrategy {

    private final PieceMovement pieceMovement;

    public JumpingStrategy(final PieceMovement pieceMovement) {
        this.pieceMovement = pieceMovement;
    }


    @Override
    public void validatePath(final Position currentPosition, final Position arrivalPosition, final Set<Piece> pieces) {
        final Movement movement = pieceMovement.getMovements().findMovements(currentPosition, arrivalPosition);
        final Path path = movement.makePath(currentPosition, arrivalPosition);

        if (computeCountExistPieceExceptLastPosition(path, pieces) != 1) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasCannon(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    @Override
    public PieceMovement getPieceMovement() {
        return pieceMovement;
    }

    private int computeCountExistPieceExceptLastPosition(final Path path, final Set<Piece> pieces) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        if (!positions.isEmpty()) {
            positions.removeLast();
        }

        return (int) positions.stream()
                .filter(pieces::contains)
                .count();
    }

    private boolean hasCannon(final Path path, final Set<Piece> pieces) {
        return path.getPositions().stream()
                .filter(pieces::contains)
                .anyMatch(position -> findCannonPiece(pieces, position));
    }

    private boolean findCannonPiece(final Set<Piece> pieces, final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(piece -> piece.matchPieceMovement(PieceMovement.CANNON));
    }
}
