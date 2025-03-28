package janggi.piece;

import janggi.setting.CampType;
import janggi.setting.GungSungCoordinate;
import janggi.value.Path;
import janggi.value.Position;
import janggi.value.RelativePath;
import janggi.value.RelativePosition;
import java.util.List;
import java.util.Optional;

public class Gung extends Piece {

    private static final List<RelativePath> RELATIVE_PATH = List.of(
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, 0))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, 0))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, 1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(0, -1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, 1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(1, -1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, 1))),
            new RelativePath(
                    List.of(new RelativePosition(0, 0), new RelativePosition(-1, -1)))
    );

    public Gung(final Position position) {
        super(PieceType.GUNG, position);
    }

    public static List<Gung> generateInitialGung(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.GUNG.getHeight());
        return PieceType.GUNG.getDefaultXPositions()
                .stream()
                .map(xPosition -> new Gung(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    protected Gung makeMovedPiece(Position position) {
        return new Gung(position);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        Optional<Path> optionalPath = calculatePath(destination);
        if (optionalPath.isEmpty()) {
            return false;
        }
        Path path = optionalPath.get();
        boolean isPathInGungSung = isPathInGungSung(path);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return isPathInGungSung && !existAlliesInDestination;
    }

    private Optional<Path> calculatePath(Position destination) {
        return RELATIVE_PATH.stream()
                .filter(route -> route.getDestination(getPosition()).equals(destination))
                .map(route -> route.calculatePath(getPosition()))
                .findFirst();
    }

    private boolean isPathInGungSung(Path path) {
        boolean isStartInRange = GungSungCoordinate.isInRange(path.getStart());
        boolean isEndInRange = GungSungCoordinate.isInRange(path.getEnd());
        return isStartInRange && isEndInRange;
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
