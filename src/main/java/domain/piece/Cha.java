package domain.piece;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private static final List<Direction> CHA_MOVABLE_DIRECTIONS = List.of(UP, RIGHT, DOWN, LEFT);

    private final Team team;

    public Cha(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(final Point source, final Point destination, final Board board) {
        return findMovablePoints(source, board).contains(destination);
    }

    private List<Point> findMovablePoints(final Point source, final Board board) {
        List<Point> candidates = new ArrayList<>(List.of(source));
        CHA_MOVABLE_DIRECTIONS.stream()
                .filter(direction -> board.existNextPoint(source, direction))
                .forEach(direction -> findCandidates(
                        board.getNextPoint(source, direction), board, direction,
                        candidates)
                );
        return candidates;
    }

    private void findCandidates(final Point currentPoint, final Board board, final Direction direction,
                                final List<Point> candidates) {
        candidates.add(currentPoint);
        if (!board.existNextPoint(currentPoint, direction)) {
            return;
        }

        Point nextPoint = board.getNextPoint(currentPoint, direction);
        if (board.matchTeam(nextPoint, this.team)) {
            return;
        }
        findCandidates(nextPoint, board, direction, candidates);
    }

    @Override
    public PieceType type() {
        return PieceType.CHA;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
