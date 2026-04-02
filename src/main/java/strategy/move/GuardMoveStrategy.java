package strategy.move;

import java.util.ArrayList;
import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.PalaceRouter;
import java.util.List;

public class GuardMoveStrategy extends MoveStrategy {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Direction.NORTH)),
            new MovePath(List.of(Direction.SOUTH)),
            new MovePath(List.of(Direction.EAST)),
            new MovePath(List.of(Direction.WEST)),
            new MovePath(List.of(Direction.NORTH_EAST)),
            new MovePath(List.of(Direction.NORTH_WEST)),
            new MovePath(List.of(Direction.SOUTH_EAST)),
            new MovePath(List.of(Direction.SOUTH_WEST))
    );

    @Override
    public List<MovePath> getPaths(Piece piece, Position from, PalaceRouter router) {
        if (!router.isInsidePalace(from)) {
            return List.of();
        }

        List<MovePath> paths = new ArrayList<>();
        for (MovePath path : PATHS) {
            Direction direction = path.steps().getFirst();
            Position destination = from.next(direction);
            if (router.isInsidePalace(destination)) {
                paths.add(path);
            }
        }
        return List.copyOf(paths);
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return pieceAtDestination == null || !pieceAtDestination.isOnTeam(myTeam);
    }
}