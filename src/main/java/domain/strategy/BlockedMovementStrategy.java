package domain.strategy;

import domain.board.PathPieces;

public class BlockedMovementStrategy implements MovementStrategy {

    private static final int BLOCKED_COUNT_WAYPOINTS = 0;

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if (pathPieces.countWaypoints() != BLOCKED_COUNT_WAYPOINTS) {
            return false;
        }

        return pathPieces.isMovableDestination();
    }
}