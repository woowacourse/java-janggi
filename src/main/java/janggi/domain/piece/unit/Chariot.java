package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.LinearPathStrategy;
import janggi.domain.piece.path.PathStrategy;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends Piece {
    private static final PieceName NAME = PieceName.CHARIOT;
    private static final PathStrategy DEFAULT_STRATEGY = new LinearPathStrategy();

    public Chariot(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
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
        return candidatePath.getPath().stream()
                .filter(piecesOnPaths::containsKey)
                .findFirst()
                .map(candidatePath::takeUntil)
                .orElse(candidatePath);
    }
}
