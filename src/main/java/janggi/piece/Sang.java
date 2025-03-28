package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Path;
import janggi.value.Position;
import janggi.value.RelativePath;
import janggi.value.RelativePosition;
import java.util.List;
import java.util.Optional;

public class Sang extends Piece {

    private static final List<RelativePath> RELATIVE_PATH = List.of(
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, 0),
                            new RelativePosition(-2, -1), new RelativePosition(-3, -2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, 0),
                            new RelativePosition(-2, 1), new RelativePosition(-3, 2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, 0),
                            new RelativePosition(2, -1), new RelativePosition(3, -2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, 0),
                            new RelativePosition(2, 1), new RelativePosition(3, 2))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, -1),
                            new RelativePosition(-1, -2), new RelativePosition(-2, -3))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, -1),
                            new RelativePosition(1, -2), new RelativePosition(2, -3))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, 1),
                            new RelativePosition(-1, 2), new RelativePosition(-2, 3))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, 1),
                            new RelativePosition(1, 2), new RelativePosition(2, 3))));

    public Sang(final Position position) {
        super(PieceType.SANG, position);
    }

    public static List<Sang> generateInitialSangs(final CampType campType, final List<Integer> xPositions) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SANG.getHeight());
        return xPositions.stream()
                .map(xPosition -> new Sang(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    protected Sang makeMovedPiece(Position position) {
        return new Sang(position);
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
        boolean existAllieInDestination = existPieceInPosition(path.getEnd(), allies);
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
