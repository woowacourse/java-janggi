package domain.piece;

import domain.Position;
import domain.Side;
import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import java.util.List;

public class King extends Piece {

//    private final List<List<Direction>> paths = List.of(
//        List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
//    );

    public King(Side side, Paths paths, PieceMoveStrategy strategy) {
        super(side, paths, strategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return strategy.findRoute(paths.getPaths(sourcePosition), sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        return "왕";
    }
}
