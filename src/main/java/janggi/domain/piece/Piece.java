package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.List;

public abstract class Piece {

    protected Position position;

    public Piece(final Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void move(
            final Position newPosition,
            final List<Piece> allyPieces,
            final List<Piece> enemyPieces
    ) {
        List<Path> moveablePaths = getMoveablePaths(allyPieces, enemyPieces);

        if (!isNewPositionExistInMoveablePath(newPosition, moveablePaths)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }

        this.position = newPosition;
    }

    protected abstract List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces);

    private boolean isNewPositionExistInMoveablePath(final Position newPosition, final List<Path> paths) {
        return paths.stream()
                .map(Path::finalPosition)
                .toList()
                .contains(newPosition);
    }

    protected List<Position> getPositionsOf(final List<List<Piece>> pieces) {
        return pieces.stream()
                .flatMap(pieces1 -> pieces1.stream()
                        .map(piece -> piece.position))
                .toList();
    }
}
