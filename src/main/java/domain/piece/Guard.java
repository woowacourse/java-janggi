package domain.piece;

import domain.vo.Position;
import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import java.util.List;

public class Guard extends Piece {

    private static final String NAME = "사";
    private static final int SCORE = 3;

    public Guard(Side side, Paths paths, PieceMoveStrategy strategy) {
        super(side, paths, strategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return strategy.findRoute(paths.getPaths(sourcePosition), sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
