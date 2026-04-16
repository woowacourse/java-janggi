package domain.piece;

import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import domain.vo.Position;
import java.util.List;

public class King extends Piece {

    private static final String NAME = "궁";

    public King(Side side, Paths paths, PieceMoveStrategy strategy) {
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
        return PieceType.KING.score;
    }
}
