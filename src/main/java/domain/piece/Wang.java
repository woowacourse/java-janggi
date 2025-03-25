package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wang implements StraightMovable {

    private static final List<Direction> WANG_MOVABLE_DIRECTIONS = Arrays.stream(Direction.values()).toList();

    private final Team team;

    public Wang(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(final Point source, final Point destination, final Board board) {
        return findMovablePoints(source, board).contains(destination);
    }

    private List<Point> findMovablePoints(final Point point, final Board board) {
        List<Point> candidates = new ArrayList<>();
        for (Direction direction : movableDirections()) {
            findCandidatesByDirection(point, direction, board, candidates);
        }
        return candidates;
    }

    private void findCandidatesByDirection(final Point point, final Direction direction,
                                           final Board board, final List<Point> candidates) {
        Point currentPoint = point;
        int stepLeft = step();
        while (stepLeft > 0) {
            if (!board.existsNextPoint(point, direction)) {
                break;
            }
            currentPoint = board.getNextPoint(point, direction);
            if (!board.existsPiece(currentPoint) || board.matchTeam(currentPoint, this.team.inverse())) {
                candidates.add(currentPoint);
            }
            stepLeft--;
        }
    }

    @Override
    public List<Direction> movableDirections() {
        return Arrays.stream(Direction.values()).toList();
    }

    @Override
    public int step() {
        return 1;
    }

    @Override
    public PieceType type() {
        return PieceType.WANG;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
