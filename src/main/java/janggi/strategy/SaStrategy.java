package janggi.strategy;

import janggi.piece.Piece;
import janggi.rule.GungSungRange;
import janggi.value.Direction;
import janggi.value.Path;
import janggi.value.Position;
import janggi.value.RelativePath;
import java.util.List;
import java.util.Optional;

public class SaStrategy implements MoveStrategy {

    private static final List<RelativePath> RELATIVE_PATH = List.of(
            new RelativePath(List.of(Direction.ORIGIN, Direction.LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.DOWN)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.DOWN_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP_RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.DOWN_RIGHT)));

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
        boolean isStartInRange = GungSungRange.isInAnyGungSung(path.getStart());
        boolean isEndInRange = GungSungRange.isInAnyGungSung(path.getEnd());
        return isStartInRange && isEndInRange;
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
