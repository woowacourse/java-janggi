package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Route;
import domain.TeamColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy extends MoveStrategy {

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        List<MovePath> paths = new ArrayList<>();

        addStraightPaths(paths, Direction.NORTH);
        addStraightPaths(paths, Direction.SOUTH);
        addStraightPaths(paths, Direction.EAST);
        addStraightPaths(paths, Direction.WEST);

        return paths;
    }

    private void addStraightPaths(List<MovePath> paths, Direction direction) {
        for (int distance = 1; distance <= 9; distance++) {
            List<Direction> steps = new ArrayList<>();
            for (int i = 0; i < distance; i++) {
                steps.add(direction);
            }
            paths.add(new MovePath(steps));
        }
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece, TeamColor myTeam) {
        if (blockingPieces.size() != 1) {
            return false;
        }

        Piece bridgePiece = blockingPieces.getFirst();
        if (bridgePiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        if (destinationPiece.isEmpty()) {
            return true;
        }

        Piece targetPiece = destinationPiece.get();
        if (targetPiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        return targetPiece.getTeamColor() != myTeam;
    }
}
