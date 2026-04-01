package strategy.move;

import java.util.ArrayList;
import java.util.List;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Route;
import domain.TeamColor;

public class CannonMoveStrategy extends MoveStrategy {

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        List<MovePath> paths = new ArrayList<>();

        paths.addAll(createStraightPaths(Direction.NORTH));
        paths.addAll(createStraightPaths(Direction.SOUTH));
        paths.addAll(createStraightPaths(Direction.EAST));
        paths.addAll(createStraightPaths(Direction.WEST));

        return paths;
    }

    private List<MovePath> createStraightPaths(Direction direction) {
        List<MovePath> paths = new ArrayList<>();
        for (int distance = 1; distance <= 9; distance++) {
            List<Direction> steps = new ArrayList<>();
            for (int i = 0; i < distance; i++) {
                steps.add(direction);
            }
            paths.add(new MovePath(steps));
        }
        return List.copyOf(paths);
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (blockingPieces.size() != 1) {
            return false;
        }

        Piece bridgePiece = blockingPieces.getFirst();
        if (bridgePiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        if (pieceAtDestination == null) {
            return true;
        }

        if (pieceAtDestination.getPieceType() == PieceType.CANNON) {
            return false;
        }

        return !pieceAtDestination.isOnTeam(myTeam);
    }
}
