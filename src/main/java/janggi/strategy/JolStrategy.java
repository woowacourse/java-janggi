package janggi.strategy;

import janggi.piece.Piece;
import janggi.rule.GungSungRange;
import janggi.value.Direction;
import janggi.value.Position;
import janggi.value.RelativePath;
import java.util.List;

public class JolStrategy implements MoveStrategy {

    private static final List<RelativePath> RELATIVE_PATH = List.of(
            new RelativePath(List.of(Direction.ORIGIN, Direction.LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP)));
    private static final List<RelativePath> RELATIVE_PATH_IN_GUNGSUNG = List.of(
            new RelativePath(List.of(Direction.ORIGIN, Direction.LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP_RIGHT)));

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        boolean isPathInGungSung = isPathInGungSung(start, destination);
        boolean existPath = existPath(start, destination, isPathInGungSung);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return existPath && !existAlliesInDestination;
    }

    private boolean existPath(Position start, Position destination, boolean isInGungSung) {
        if (isInGungSung) {
            return RELATIVE_PATH_IN_GUNGSUNG.stream()
                    .anyMatch(path -> path.getDestination(start).equals(destination));
        }
        return RELATIVE_PATH.stream()
                .anyMatch(path -> path.getDestination(start).equals(destination));
    }

    private boolean isPathInGungSung(Position start, Position destination) {
        boolean isStartInRange = GungSungRange.isInAnyGungSung(start);
        boolean isEndInRange = GungSungRange.isInAnyGungSung(destination);
        return isStartInRange && isEndInRange;
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
