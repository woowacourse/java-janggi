package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public abstract class FixedPathStrategy implements PathStrategy {
    final List<Movement> possibleMovements;

    FixedPathStrategy(List<Movement> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor){
        return possibleMovements.stream()
                .anyMatch(path::canReachToDestination);
    }

    public List<Position> findAllRoute(PiecePath path) {
        Movement movement = possibleMovements.stream()
                .filter(path::canReachToDestination)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("해당 경로가 유효하지 않아 이동경로를 찾을 수 없습니다."));

        return path.tracePositionsByDirection(movement);
    }
}
