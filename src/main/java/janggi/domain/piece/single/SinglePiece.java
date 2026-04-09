package janggi.domain.piece.single;

import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.path.generator.SinglePathStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;
import java.util.List;

public abstract class SinglePiece extends Piece {
    private static final PathStrategy SINGLE_PATH = new SinglePathStrategy();
    private static final List<Movement> MOVEMENTS = createMovements();

    protected SinglePiece(PieceName name, Side side, Score score) {
        super(name, side, SINGLE_PATH, score);
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
