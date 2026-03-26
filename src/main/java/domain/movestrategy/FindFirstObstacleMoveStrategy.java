package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class FindFirstObstacleMoveStrategy extends DefaultMoveStrategy {

    public Optional<Piece> findFirstObstacle(List<Position> path, Position from, Map<Position, Piece> pieces) {
        for (Position delta : path) {
            Position nextPosition = from.move(delta);
            if (pieces.containsKey(nextPosition)) {
                return Optional.of(pieces.get(nextPosition));
            }
        }
        return Optional.empty();
    }

    public List<Position> getPositionsBeforeObstacle(List<Position> path, Position from, Map<Position, Piece> pieces) {
        List<Position> result = new ArrayList<>();
        for (Position delta : path) {
            Position nextPosition = from.move(delta);
            result.add(nextPosition);

            if (pieces.containsKey(nextPosition)) {
                return result;
            }
        }
        return result;
    }

    public List<Position> getPositionsAfterObstacle(List<Position> path, Position from, Map<Position, Piece> pieces) {
        List<Position> result = new ArrayList<>();
        boolean metObstacle = false;
        for (Position delta : path) {
            Position nextPosition = from.move(delta);
            if (pieces.containsKey(nextPosition)) {
                metObstacle = true;
                continue;
            }
            if (metObstacle) {
                result.add(nextPosition);
            }
        }
        return result;
    }
}
