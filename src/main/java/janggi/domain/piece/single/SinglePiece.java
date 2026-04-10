package janggi.domain.piece.single;

import janggi.domain.board.BoardInfo;
import janggi.domain.piece.PalacePiece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Score;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.piece.path.generator.PathStrategy;
import janggi.domain.piece.path.generator.SinglePathStrategy;
import janggi.domain.side.Side;
import java.util.List;

public abstract class SinglePiece extends PalacePiece {
    private static final PathStrategy SINGLE_PATH = new SinglePathStrategy();
    private static final List<Movement> MOVEMENTS = createMovements();

    protected SinglePiece(PieceType name, Side side, Score score) {
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
    protected boolean isValidPath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return !candidatePath.isEmpty();
    }

    @Override
    protected List<Movement> getMovements() {
        return MOVEMENTS;
    }
}
