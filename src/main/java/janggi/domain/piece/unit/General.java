package janggi.domain.piece.unit;

import janggi.domain.point.Point;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.path.generator.FixedPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class General extends Piece {
    private static final PieceName NAME = PieceName.GENERAL;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public General(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Movement> createCandidateMovement() {
        return Collections.EMPTY_LIST;
//        List<Movement> movements = new ArrayList<>();
//        for (Direction value : Direction.values()) {
//            Movement path = new Movement(List.of(value));
//            movements.add(path);
//        }
//
//        return movements;
    }


    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath;
    }
}
