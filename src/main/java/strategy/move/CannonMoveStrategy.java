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
    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        if (!hasExactlyOneBridge(blockingPieces)) {
            return false;
        }

        final Piece bridgePiece = blockingPieces.getFirst();
        if (!isValidBridge(bridgePiece)) {
            return false;
        }

        return destinationPiece
                .map(this::isCapturableTarget)
                .orElse(true);
    }

    private boolean hasExactlyOneBridge(List<Piece> blockingPieces) {
        return blockingPieces.size() == REQUIRED_BRIDGE_COUNT;
    }

    private boolean isValidBridge(Piece bridgePiece) {
        return bridgePiece.getPieceType() != PieceType.CANNON;
    }

    private boolean isCapturableTarget(Piece targetPiece) {
        return targetPiece.getPieceType() != PieceType.CANNON;
    }
}
