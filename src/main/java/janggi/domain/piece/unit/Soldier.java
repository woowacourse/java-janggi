package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.FixedPathStrategy;
import janggi.domain.piece.path.PathStrategy;
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
    public List<Movement> createCandidateMovement() {
        List<Movement> directions = new ArrayList<>();
        if (Side.CHO.equals(side)) {
            directions.add(new Movement(List.of(Direction.NORTH)));
            directions.add(new Movement(List.of(Direction.WEST)));
            directions.add(new Movement(List.of(Direction.EAST)));
        }
        if (Side.HAN.equals(side)) {
            directions.add(new Movement(List.of(Direction.SOUTH)));
            directions.add(new Movement(List.of(Direction.WEST)));
            directions.add(new Movement(List.of(Direction.EAST)));
        }
        return directions;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath;
    }
}
