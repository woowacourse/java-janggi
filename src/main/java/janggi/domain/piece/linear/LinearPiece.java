package janggi.domain.piece.linear;

import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.path.generator.LinearPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;
import java.util.List;

public abstract class LinearPiece extends Piece {
    private static final PathStrategy LINEAR_STRATEGY = new LinearPathStrategy();
    private static final List<Movement> MOVEMENTS = createMovements();

    protected LinearPiece(PieceName name, Side side, Score score) {
        super(name, side, LINEAR_STRATEGY, score);
    }

    private static List<Movement> createMovements() {
        return List.of(
                new Movement(Direction.NORTH),
                new Movement(Direction.SOUTH),
                new Movement(Direction.WEST),
                new Movement(Direction.EAST));
    }

    @Override
    public List<Movement> getMovements() {
        return MOVEMENTS;
    }
}
