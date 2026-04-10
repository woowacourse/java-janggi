package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.TeamColor;
import domain.palace.PalaceRouter;
import java.util.List;

public class PawnMoveStrategy extends MoveStrategy {
    @Override
    public List<MovePath> getPaths(Piece piece, Position from, PalaceRouter router) {
        if (piece.getTeamColor() == TeamColor.CHO) {
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
