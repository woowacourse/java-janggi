package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.Palace;
import java.util.List;

public class KingMoveStrategy extends MoveStrategy {

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
    public List<MovePath> getPaths(Piece piece) {
        return PATHS;
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return pieceAtDestination == null || !pieceAtDestination.isOnTeam(myTeam);
    }
}
