package domain.piece;

import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import domain.Position;
import domain.Side;
import java.util.List;

public class General extends Piece {
    public General(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        return filterStandardPaths(paths, board);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    public String toString() {
        return "궁";
    }
}
