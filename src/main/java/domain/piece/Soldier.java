package domain.piece;

import domain.Position;
import domain.Side;
import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import java.util.List;

public class Soldier extends Piece {

//    private final List<List<Direction>> paths;

    public Soldier(Side side, Paths paths, PieceMoveStrategy strategy) {
        super(side, paths, strategy);
//        if (Side.CHO == side) {
//            paths = List.of(
//                List.of(Direction.UP), List.of(Direction.RIGHT), List.of(Direction.LEFT)
//            );
//            return;
//        }
//        paths = List.of(
//            List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
//        );
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return strategy.findRoute(paths.getPaths(sourcePosition), sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "졸";
        }
        return "병";
    }
}
