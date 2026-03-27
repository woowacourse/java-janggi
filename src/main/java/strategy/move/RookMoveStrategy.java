package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.TeamColor;
import java.util.List;

public class RookMoveStrategy implements MoveStrategy {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Direction.NORTH)),
            new MovePath(List.of(Direction.SOUTH)),
            new MovePath(List.of(Direction.EAST)),
            new MovePath(List.of(Direction.WEST))
    );

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        return PATHS;
    }
}
