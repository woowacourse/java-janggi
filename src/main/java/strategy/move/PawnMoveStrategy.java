package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class PawnMoveStrategy implements MoveStrategy{


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

    @Override
    public boolean canJump(List<Piece> blockingPieces) {
        if (blockingPieces.isEmpty()) {
            return true;
        }
        return false;
    }

}
