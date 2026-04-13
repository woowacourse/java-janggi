package strategy.move;

import domain.board.Direction;
import domain.board.MovePath;
import domain.piece.TeamColor;
import java.util.List;

public class PawnMoveStrategy implements MoveStrategy {
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
