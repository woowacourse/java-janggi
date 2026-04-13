package domain.piece;

import domain.common.Position;
import domain.common.Side;
import domain.board.BoardReader;
import domain.movement.strategy.MovementStrategy;
import domain.movement.Path;
import java.util.List;

public class Guard extends Piece {
    private final PieceType pieceType = PieceType.GUARD;

    public Guard(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(List<Path> paths, BoardReader board) {
        return filterStandardPaths(paths, board);
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String getName() {
        return pieceType.getName();
    }
}
