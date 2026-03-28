package strategy.move;

import domain.board.Direction;
import domain.board.MovePath;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {
    private static final int MIN_DISTANCE = 1;
    private static final int MAX_STRAIGHT_DISTANCE = 9;
    private static final int REQUIRED_BRIDGE_COUNT = 1;

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        final List<MovePath> paths = new ArrayList<>();

        addStraightPaths(paths, Direction.NORTH);
        addStraightPaths(paths, Direction.SOUTH);
        addStraightPaths(paths, Direction.EAST);
        addStraightPaths(paths, Direction.WEST);

        return paths;
    }

    private void addStraightPaths(List<MovePath> paths, Direction direction) {
        for (int distance = MIN_DISTANCE; distance <= MAX_STRAIGHT_DISTANCE; distance++) {
            final List<Direction> steps = new ArrayList<>();
            for (int i = 0; i < distance; i++) {
                steps.add(direction);
            }
            paths.add(new MovePath(steps));
        }
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece, TeamColor myTeam) {
        if (blockingPieces.size() != REQUIRED_BRIDGE_COUNT) {
            return false;
        }

        final Piece bridgePiece = blockingPieces.getFirst();
        if (bridgePiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        if (destinationPiece.isEmpty()) {
            return true;
        }

        final Piece targetPiece = destinationPiece.get();
        if (targetPiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        return targetPiece.getTeamColor() != myTeam;
    }
}


