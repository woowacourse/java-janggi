package strategy.move;

import domain.BlockingPieces;
import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;

public class GuardMoveStrategy implements MoveStrategy {

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
    public List<MovePath> getPaths(TeamColor teamColor) {
        return PATHS;
    }

    @Override
    public boolean canJump(BlockingPieces blockingPieces, TeamColor myTeam) {
        if (blockingPieces.size() == 0) {
            return true;
        }

        if (blockingPieces.size() == 1) {
            TeamColor targetTeam = blockingPieces.getFirstBlockingTeamColor();
            return targetTeam != myTeam;
        }

        return false;
    }
}
