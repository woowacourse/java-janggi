package domain.piece;

import domain.game.Position;
import domain.game.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.List;

public class General extends Piece {
    private final PieceType pieceType = PieceType.GENERAL;

    public General(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(List<Path> paths, BoardReader board) {
        return filterStandardPaths(paths, board);
    }

    @Override
    public boolean isGeneral() {
        return true;
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
