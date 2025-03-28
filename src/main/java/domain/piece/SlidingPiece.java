package domain.piece;

import domain.board.PieceVisibleBoard;
import domain.piece.character.Team;
import domain.point.Direction;
import domain.point.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class SlidingPiece extends Piece {

    public SlidingPiece(Team team) {
        super(team);
    }

    @Override
    protected List<Point> findMovablePoints(final Point point, final PieceVisibleBoard board) {
        List<Point> candidates = new ArrayList<>();
        for (Direction direction : movableDirections()) {
            candidates.addAll(findCandidatesByDirection(point, direction, board));
        }
        return candidates;
    }

    private List<Point> findCandidatesByDirection(final Point point, final Direction direction,
                                                  final PieceVisibleBoard board) {
        List<Point> candidates = new ArrayList<>();
        Point currentPoint = point;
        for (int stepLeft = maxStep(); stepLeft > 0 && board.existsNextPoint(currentPoint, direction); stepLeft--) {
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

    abstract int maxStep();
}
