package domain.piece;

import domain.position.Path;
import domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private final Position position;
    private final PieceType type;

    public Piece(final Position position, final PieceType type) {
        this.position = position;
        this.type = type;
    }

    public Piece move(
            final Position newPosition,
            final List<Piece> allyPieces,
            final List<Piece> enemyPieces
    ) {
        List<Path> moveablePaths = type.getMoveablePaths(position);
        moveablePaths = filterMiddleBlocked(moveablePaths, allyPieces, enemyPieces);
        moveablePaths = filterFinalIsAlly(moveablePaths, allyPieces);
        moveablePaths = filterFinalIsPoWhenTypeIsPo(moveablePaths, enemyPieces);

        if (!isNewPositionExistInMoveablePath(newPosition, moveablePaths)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }

        return new Piece(newPosition, type);
    }

    private List<Path> filterFinalIsPoWhenTypeIsPo(final List<Path> paths, final List<Piece> enemyPieces) {
        if (type != PieceType.포) {
            return paths;
        }

        final List<Piece> poPiece = enemyPieces.stream()
                .filter(piece -> piece.type == PieceType.포)
                .toList();

        return paths.stream()
                .filter(path -> !path.isEncounteredLast(poPiece))
                .toList();
    }

    private List<Path> filterMiddleBlocked(
            final List<Path> paths,
            final List<Piece> allyPieces,
            final List<Piece> enemyPieces
    ) {
        final List<Piece> hello = new ArrayList<>();
        hello.addAll(allyPieces);
        hello.addAll(enemyPieces);

        return paths.stream()
                .filter(path -> {
                    if (type == PieceType.포) {
                        List<Piece> piece = path.getEncounteredMiddlePieces(hello);
                        return piece.size() == 1 && piece.getFirst().type != PieceType.포;
                    } else {
                        return path.getEncounteredMiddlePieces(hello).isEmpty();
                    }
                })
                .toList();
    }

    private List<Path> filterFinalIsAlly(final List<Path> paths, final List<Piece> allyPieces) {
        return paths.stream()
                .filter(path -> !path.isEncounteredLast(allyPieces))
                .toList();
    }

    private static boolean isNewPositionExistInMoveablePath(final Position newPosition, final List<Path> paths) {
        return paths.stream()
                .map(Path::getFinalPosition)
                .toList()
                .contains(newPosition);
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + position +
                ", type=" + type +
                '}';
    }
}
