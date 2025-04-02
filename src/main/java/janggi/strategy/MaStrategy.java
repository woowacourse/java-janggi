package janggi.strategy;

import janggi.piece.Piece;
import janggi.value.Direction;
import janggi.value.Path;
import janggi.value.Position;
import janggi.value.RelativePath;
import java.util.List;
import java.util.Optional;

public class MaStrategy implements MoveStrategy {

    private static final List<RelativePath> RELATIVE_PATH = List.of(
            new RelativePath(List.of(Direction.ORIGIN, Direction.LEFT, Direction.UP_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.LEFT, Direction.DOWN_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.RIGHT, Direction.UP_RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.RIGHT, Direction.DOWN_RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP, Direction.UP_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.UP, Direction.UP_RIGHT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.DOWN, Direction.DOWN_LEFT)),
            new RelativePath(List.of(Direction.ORIGIN, Direction.DOWN, Direction.DOWN_RIGHT))
    );


    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        Optional<Path> optionalPath = calculatePath(start, destination);
        if (optionalPath.isEmpty()) {
            return false;
        }
        Path path = optionalPath.get();
        boolean existEnemyInPath = existPieceInPath(path, enemy);
        boolean existAlliesInPath = existPieceInPath(path, allies);
        boolean existAllieInDestination = existPieceInPosition(destination, allies);
        return !existEnemyInPath && !existAlliesInPath && !existAllieInDestination;
    }

    private Optional<Path> calculatePath(Position start, Position destination) {
        return RELATIVE_PATH.stream()
                .filter(route -> route.getDestination(start).equals(destination))
                .map(route -> route.calculatePath(start))
                .findFirst();
    }

    private boolean existPieceInPath(Path path, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> path.isInMiddle(piece.getPosition()));
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }
}
