package domain.piece;

import domain.vo.Position;
import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import java.util.List;

public class Soldier extends Piece {

    private static final String CHO_NAME = "졸";
    private static final String HAN_NAME = "병";
    private static final int SCORE = 2;

    public Soldier(Side side, Paths paths, PieceMoveStrategy strategy) {
        super(side, paths, strategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return strategy.findRoute(paths.getPaths(sourcePosition), sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return CHO_NAME;
        }
        return HAN_NAME;
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
