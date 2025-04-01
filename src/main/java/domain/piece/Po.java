package domain.piece;

import domain.board.PieceVisibleBoard;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Direction;
import domain.point.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    protected List<Point> findMovablePoints(final Point source, final PieceVisibleBoard board) {
        return movableDirections().stream()
                .filter(direction -> existsHurdle(source, direction, board))
                .flatMap(direction -> {
                    Point hurdle = findHurdle(source, direction, board);
                    return findCandidates(hurdle, direction, board).stream();
                })
                .toList();
    }

    private boolean existsHurdle(final Point point, final Direction direction, final PieceVisibleBoard board) {
        Point currentPoint = point;
        while (board.existsNextPoint(currentPoint, direction)) {
            currentPoint = board.getNextPoint(currentPoint, direction);
            if (board.existsPiece(currentPoint) && !board.existsPo(currentPoint)) {
                return true;
            }
        }
        return false;
    }

    private Point findHurdle(final Point point, final Direction direction, final PieceVisibleBoard board) {
        Point currentPoint = point;
        while (board.existsNextPoint(currentPoint, direction)) {
            currentPoint = board.getNextPoint(currentPoint, direction);
            if (board.existsPiece(currentPoint) && !board.existsPo(currentPoint)) {
                return currentPoint;
            }
        }
        throw new IllegalStateException("[ERROR] 뛰어넘을 기물(허들)을 찾을 수 없습니다.");
    }

    private List<Point> findCandidates(final Point point, final Direction direction, final PieceVisibleBoard board) {
        List<Point> candidates = new ArrayList<>();
        Point currentPoint = point;
        while (board.existsNextPoint(currentPoint, direction)) {
            currentPoint = board.getNextPoint(currentPoint, direction);
            if (board.existsPo(currentPoint) || board.matchTeam(currentPoint, team())) {
                break;
            }
            candidates.add(currentPoint);
            if (board.matchTeam(currentPoint, team().inverse())) {
                break;
            }
        }
        return candidates;
    }

    public List<Direction> movableDirections() {
        return Arrays.stream(Direction.values()).toList();
    }

    @Override
    public boolean isOnlyMovableInPalace() {
        return false;
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
