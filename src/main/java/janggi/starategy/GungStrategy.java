package janggi.starategy;

import janggi.piece.Piece;
import janggi.setting.GungSungCoordinate;
import janggi.value.Path;
import janggi.value.Position;
import janggi.value.RelativePath;
import janggi.value.RelativePosition;
import java.util.List;
import java.util.Optional;

public class GungStrategy implements MoveStrategy {

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

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        Optional<Path> optionalPath = calculatePath(start, destination);
        if (optionalPath.isEmpty()) {
            return false;
        }
        Path path = optionalPath.get();
        boolean isPathInGungSung = isPathInGungSung(path);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return isPathInGungSung && !existAlliesInDestination;
    }

    private Optional<Path> calculatePath(Position start, Position destination) {
        return RELATIVE_PATH.stream()
                .filter(route -> route.getDestination(start).equals(destination))
                .map(route -> route.calculatePath(start))
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
