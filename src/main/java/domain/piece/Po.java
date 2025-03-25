package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    public List<Point> findMovablePoints(final Point source, final Board board) {
        List<Point> candidates = new ArrayList<>();
        movableDirections().stream()
                .filter(direction -> existsHurdle(source, direction, board))
                .forEach(direction -> {
                    Point hurdle = findHurdle(source, direction, board);
                    findCandidates(hurdle, direction, board, candidates);
                });
        return candidates;
    }

    private boolean existsHurdle(final Point currentPoint, final Direction direction, final Board board) {
        if (!board.existsNextPoint(currentPoint, direction)) {
            return false;
        }
        Point nextPoint = board.getNextPoint(currentPoint, direction);
        if (board.existsPiece(nextPoint) && !board.existsPo(nextPoint)) {
            return true;
        }
        return existsHurdle(nextPoint, direction, board);
    }

    private Point findHurdle(final Point currentPoint, final Direction direction, final Board board) {
        if (!board.existsNextPoint(currentPoint, direction)) {
            throw new IllegalArgumentException("이동할 경로가 없습니다.");
        }
        Point nextPoint = board.getNextPoint(currentPoint, direction);
        if (board.existsPiece(nextPoint) && !board.existsPo(nextPoint)) {
            return nextPoint;
        }
        return findHurdle(nextPoint, direction, board);
    }

    private void findCandidates(final Point currentPoint, final Direction direction, final Board board,
                                final List<Point> candidates) {
        if (!board.existsNextPoint(currentPoint, direction)) {
            return;
        }
        Point nextPoint = board.getNextPoint(currentPoint, direction);
        if (board.existsPo(nextPoint) || board.matchTeam(nextPoint, team())) {
            return;
        }
        if (board.matchTeam(nextPoint, team().inverse())) {
            candidates.add(nextPoint);
            return;
        }
        candidates.add(nextPoint);
        findCandidates(nextPoint, direction, board, candidates);
    }

    public List<Direction> movableDirections() {
        return Arrays.stream(Direction.values()).toList();
    }

    @Override
    public PieceType type() {
        return PieceType.PO;
    }

    @Override
    public int score() {
        return 7;
    }
}
