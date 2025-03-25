package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class SlidingPiece extends Piece {

    public SlidingPiece(Team team) {
        super(team);
    }

    @Override
    public List<Point> findMovablePoints(final Point point, final Board board) {
        List<Point> candidates = new ArrayList<>();
        for (Direction direction : movableDirections()) {
            candidates.addAll(findCandidatesByDirection(point, direction, board));
        }
        return candidates;
    }

    private List<Point> findCandidatesByDirection(final Point point, final Direction direction,
                                                  final Board board) {
        List<Point> candidates = new ArrayList<>();
        Point currentPoint = point;
        for (int stepLeft = step(); stepLeft > 0 && board.existsNextPoint(currentPoint, direction); stepLeft--) {
            currentPoint = board.getNextPoint(currentPoint, direction);
            if (board.matchTeam(currentPoint, team())) {
                break;
            }
            candidates.add(currentPoint);
            if (board.matchTeam(currentPoint, team().inverse())) {
                break;
            }
        }
        return candidates;
    }

    abstract List<Direction> movableDirections();

    abstract int step();
}
