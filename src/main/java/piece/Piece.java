package piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import position.Path;
import position.Position;

public final class Piece {

    private final Position position;
    private final PieceType type;

    public PieceType getType() {
        return type;
    }

    public Piece(final Position position, final PieceType type) {
        this.position = position;
        this.type = type;
    }

    public Piece movePiece(
            final Position newPosition,
            final List<Piece> allyPieces,
            final List<Piece> enemyPieces
    ) {
        List<Path> movablePaths = type.getMovablePaths(position);
        movablePaths = filterMiddleBlocked(movablePaths, allyPieces, enemyPieces);
        movablePaths = filterFinalIsAlly(movablePaths, allyPieces);
        movablePaths = filterFinalIsPoWhenTypeIsPo(movablePaths, enemyPieces);
        if (!isNewPositionExistInMovablePath(newPosition, movablePaths)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }

        return new Piece(newPosition, type);
    }

    private List<Path> filterFinalIsPoWhenTypeIsPo(final List<Path> paths, final List<Piece> enemyPieces) {
        if (type != PieceType.CANNON) {
            return paths;
        }

        final List<Piece> poPiece = enemyPieces.stream()
                .filter(piece -> piece.type == PieceType.CANNON)
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
        final List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(allyPieces);
        allPieces.addAll(enemyPieces);

        return paths.stream()
                .filter(middleBlockPathPredicate(allPieces))
                .toList();
    }

    private Predicate<Path> middleBlockPathPredicate(List<Piece> allPieces) {
        return path -> {
            if (type == PieceType.CANNON) {
                List<Piece> piece = path.getEncounteredMiddlePieces(allPieces);
                return piece.size() == 1 && piece.getFirst().type != PieceType.CANNON;
            } else {
                return path.getEncounteredMiddlePieces(allPieces).isEmpty();
            }
        };
    }

    private List<Path> filterFinalIsAlly(final List<Path> paths, final List<Piece> allyPieces) {
        return paths.stream()
                .filter(path -> !path.isEncounteredLast(allyPieces))
                .toList();
    }

    private static boolean isNewPositionExistInMovablePath(final Position newPosition, final List<Path> paths) {
        return paths.stream()
                .map(Path::finalPosition)
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
