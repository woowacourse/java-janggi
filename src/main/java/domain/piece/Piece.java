package domain.piece;

import domain.position.Path;
import domain.position.Position;

import java.util.List;

public class Piece {

    private final Position position;
    private final PieceType type;

    public Piece(final Position position, final PieceType type) {
        this.position = position;
        this.type = type;
    }

    public Piece move(final Position newPosition, final List<Piece> otherPieces) {
        List<Path> moveablePaths = type.getMoveablePaths(position);
        List<Path> notBlockedPaths = filterBlocked(moveablePaths, otherPieces);

        if (!isNewPositionExistInMoveablePath(newPosition, notBlockedPaths)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }

        return new Piece(newPosition, type);
    }

    private List<Path> filterBlocked(final List<Path> moveablePaths, final List<Piece> otherPieces) {
        List<Position> otherPiecesPosition = otherPieces.stream()
                .map(piece -> piece.position)
                .toList();
        return moveablePaths.stream()
                .filter(path -> !path.havePosition(otherPiecesPosition))
                .toList();
    }

    private static boolean isNewPositionExistInMoveablePath(final Position newPosition, final List<Path> notBlockedPaths) {
        return notBlockedPaths.stream()
                .map(Path::getFinalPosition)
                .toList()
                .contains(newPosition);
    }
}
