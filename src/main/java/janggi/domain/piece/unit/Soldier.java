package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.path.generator.FixedPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final PieceName NAME = PieceName.SOLDIER;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Soldier(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    protected boolean isValidPath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        if (candidatePath.isEmpty()) {
            return false;
        }
        if (side.equals(Side.HAN) && candidatePath.isForward(Direction.NORTH)) {
            return false;
        }
        if (side.equals(Side.CHO) && candidatePath.isForward(Direction.SOUTH)) {
            return false;
        }

        return true;
    }

    @Override
    public List<Movement> createCandidateMovement() {
        List<Movement> movements = new ArrayList<>();
        movements.add(new Movement(List.of(Direction.NORTH)));
        movements.add(new Movement(List.of(Direction.SOUTH)));
        movements.add(new Movement(List.of(Direction.WEST)));
        movements.add(new Movement(List.of(Direction.EAST)));

        return movements;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath;
    }
}
