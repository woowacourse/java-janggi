package domain.piece;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.DOWN_LEFT;
import static domain.board.Direction.DOWN_RIGHT;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;
import static domain.board.Direction.UP_LEFT;
import static domain.board.Direction.UP_RIGHT;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Byeong implements StraightMovable {

    private static final List<Direction> CHO_BYEONG_MOVABLE_DIRECTIONS
            = List.of(LEFT, UP_LEFT, UP, UP_RIGHT, RIGHT);
    private static final List<Direction> HAN_BYEONG_MOVABLE_DIRECTIONS
            = List.of(LEFT, DOWN_LEFT, DOWN, DOWN_RIGHT, RIGHT);
    private static final Map<Team, List<Direction>> DIRECTIONS_BY_TEAM = Map.ofEntries(
            Map.entry(Team.CHO, CHO_BYEONG_MOVABLE_DIRECTIONS),
            Map.entry(Team.HAN, HAN_BYEONG_MOVABLE_DIRECTIONS)
    );

    private final Team team;

    public Byeong(Team team) {
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
        return DIRECTIONS_BY_TEAM.get(this.team);
    }

    @Override
    public int step() {
        return 1;
    }

    @Override
    public PieceType type() {
        return PieceType.BYEONG;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
