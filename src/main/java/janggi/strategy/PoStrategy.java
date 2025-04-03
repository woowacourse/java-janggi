package janggi.strategy;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.rule.GungSungRange;
import janggi.value.Direction;
import janggi.value.Path;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PoStrategy implements MoveStrategy {

    private static final List<Direction> DIRECTIONS_OUT_OF_GUNGSUNG =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    private static final List<Direction> DIRECTIONS_IN_OF_GUNGSUNG =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                    Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT);

    @Override
    public boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies) {
        Optional<Path> optionalPath = calculatePath(start, destination);
        if (optionalPath.isEmpty()) {
            return false;
        }
        Path path = optionalPath.get();
        List<Piece> piecesInPath = searchPieceInPath(path, enemy, allies);
        boolean existOnlyOnePieceInPath = existOnlyOnePieceInPath(piecesInPath);
        boolean existPoInPath = existPoInPath(piecesInPath);
        boolean existAlliesInDestination = existPieceInPosition(destination, allies);
        return existOnlyOnePieceInPath && !existPoInPath && !existAlliesInDestination;
    }

    private Optional<Path> calculatePath(Position start, Position destination) {
        boolean isPathInGungsung = isPathInGungsung(start, destination);
        Direction direction = Direction.parse(start, destination);
        if (!isPossibleDirection(direction, isPathInGungsung)) {
            return Optional.empty();
        }
        List<Position> positionsInDirection = direction.calculatePositionInDirection(start, destination);
        return Optional.of(new Path(positionsInDirection));
    }

    private List<Piece> searchPieceInPath(Path path, List<Piece> enemy, List<Piece> allies) {
        ArrayList<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(enemy);
        allPieces.addAll(allies);
        return allPieces.stream()
                .filter(piece -> path.isInMiddle(piece.getPosition()))
                .toList();
    }

    private boolean existOnlyOnePieceInPath(List<Piece> piecesInPath) {
        return piecesInPath.size() == 1;
    }

    private boolean existPoInPath(List<Piece> piecesInPath) {
        long poCount = piecesInPath.stream()
                .filter(alliesPiece -> alliesPiece.checkPieceType(PieceType.PO))
                .count();
        return poCount > 0;
    }

    private boolean existPieceInPosition(Position position, List<Piece> pieces) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }

    private boolean isPathInGungsung(Position start, Position destination) {
        return GungSungRange.isInAnyGungSung(start) && GungSungRange.isInAnyGungSung(destination);
    }

    private boolean isPossibleDirection(Direction direction, boolean isPathInGungsung) {
        if (isPathInGungsung) {
            return DIRECTIONS_IN_OF_GUNGSUNG.contains(direction);
        }
        return DIRECTIONS_OUT_OF_GUNGSUNG.contains(direction);
    }
}
