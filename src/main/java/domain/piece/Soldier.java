package domain.piece;

import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import domain.vo.Position;
import java.util.List;

public class Soldier extends Piece {

    private static final String CHO_NAME = "졸";
    private static final String HAN_NAME = "병";

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
        return PieceType.SOLDIER.score;
    }
}
