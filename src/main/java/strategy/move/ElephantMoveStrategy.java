package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.palace.PalaceRouter;
import java.util.List;

public class ElephantMoveStrategy extends MoveStrategy {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
            new MovePath(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
            new MovePath(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
            new MovePath(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST)),
            new MovePath(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST)),
            new MovePath(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
            new MovePath(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST)),
            new MovePath(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST))
    );

    @Override
    public List<MovePath> getPaths(Piece piece, Position from, PalaceRouter router) {
        return PATHS;
    }
}
