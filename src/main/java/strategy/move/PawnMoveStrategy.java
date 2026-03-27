package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.TeamColor;
import java.util.List;

public class PawnMoveStrategy implements MoveStrategy{

    private static final List<MovePath> PATHS = List.of(
            // 북쪽 베이스
            new MovePath(List.of(Direction.NORTH)),
            // 남쪽 베이스
            new MovePath(List.of(Direction.SOUTH)),
            // 동쪽 베이스
            new MovePath(List.of(Direction.EAST)),
            // 서쪽 베이스
            new MovePath(List.of(Direction.WEST))
    );

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
