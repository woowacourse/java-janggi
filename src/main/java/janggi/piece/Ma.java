package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Path;
import janggi.value.Position;
import janggi.value.RelativePath;
import janggi.value.RelativePosition;
import java.util.List;
import java.util.Optional;

public class Ma extends Piece {

    private static final List<RelativePath> RELATIVE_PATH = List.of(
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, 0), new RelativePosition(-2, -1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, 0), new RelativePosition(-2, 1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, 0), new RelativePosition(2, -1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, 0), new RelativePosition(2, 1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, -1), new RelativePosition(-1, -2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, -1), new RelativePosition(1, -2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, 1), new RelativePosition(-1, 2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, 1), new RelativePosition(1, 2)))
    );

    public Ma(final Position position) {
        super(PieceType.MA, position);
    }

    public static List<Ma> generateInitialMas(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.MA.getHeight());
        return xPositions.stream()
                .map(xPosition -> new Ma(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    protected Ma makeMovedPiece(Position position) {
        return new Ma(position);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        Optional<Path> optionalPath = calculatePath(destination);
        if (optionalPath.isEmpty()) {
            return false;
        }
        Path path = optionalPath.get();
        boolean existEnemyInPath = existPieceInPath(path, enemy);
        boolean existAlliesInPath = existPieceInPath(path, allies);
        boolean existAllieInDestination = existPieceInPosition(destination, allies);
        return !existEnemyInPath && !existAlliesInPath && !existAllieInDestination;
    }

    private Optional<Path> calculatePath(Position destination) {
        return RELATIVE_PATH.stream()
                .filter(route -> route.getDestination(getPosition()).equals(destination))
                .map(route -> route.calculatePath(getPosition()))
                .findFirst();
    }

    private boolean existPieceInPath(Path path, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> path.isInMiddle(piece.getPosition()));
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
