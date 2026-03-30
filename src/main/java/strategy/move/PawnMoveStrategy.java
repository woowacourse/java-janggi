package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.TeamColor;
import java.util.List;

public class PawnMoveStrategy extends MoveStrategy {


    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return List.of(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.EAST)),
                    new MovePath(List.of(Direction.WEST))
            );
        }

        return List.of(
                new MovePath(List.of(Direction.SOUTH)),
                new MovePath(List.of(Direction.EAST)),
                new MovePath(List.of(Direction.WEST))
        );
    }
}
