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
        int stepLeft = step();
        while (stepLeft > 0) {
            if (!board.existsNextPoint(currentPoint, direction)) {
                break;
            }

            currentPoint = board.getNextPoint(currentPoint, direction);
            if (board.existsPiece(currentPoint) && board.matchTeam(currentPoint, team())) {
                break;
            }
            candidates.add(currentPoint);
            if (board.existsPiece(currentPoint) && board.matchTeam(currentPoint, team().inverse())) {
                break;
            }
            stepLeft--;
        }
        return candidates;
    }

    abstract List<Direction> movableDirections();

    abstract int step();
}
