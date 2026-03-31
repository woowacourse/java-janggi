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

public class Cannon extends Piece {
    private static final PieceName NAME = PieceName.CANNON;
    private static final PathStrategy DEFAULT_STRATEGY = new LinearPathStrategy();

    public Cannon(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    protected List<Movement> createCandidateMovement() {
        List<Movement> movements = new ArrayList<>();
        movements.add(new Movement(List.of(Direction.NORTH)));
        movements.add(new Movement(List.of(Direction.SOUTH)));
        movements.add(new Movement(List.of(Direction.WEST)));
        movements.add(new Movement(List.of(Direction.EAST)));

        return movements;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        List<Point> points = candidatePath.getPath();
        int count = 0;
        List<Point> cutPoints = new ArrayList<>();

        for (Point point : points) {
            if (piecesOnPaths.getOrDefault(point, null) instanceof Cannon) {
                break;
            }
            if (piecesOnPaths.containsKey(point)) {
                count++;
                if (count == 1) {
                    continue;
                }
            }
            if (count >= 1) {
                cutPoints.add(point);
            }
            if (count == 2) {
                break;
            }
        }
        return new CandidatePath(cutPoints);
    }
}
