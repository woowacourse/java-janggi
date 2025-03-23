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
import java.util.List;
import java.util.Map;

public class Byeong implements Piece {

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
        return DIRECTIONS_BY_TEAM.get(this.team).stream()
                .filter(direction -> board.existsNextPoint(point, direction))
                .map(direction -> board.getNextPoint(point, direction))
                .filter(nextPoint -> !(board.existsPiece(nextPoint) && board.matchTeam(nextPoint, this.team)))
                .toList();
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
