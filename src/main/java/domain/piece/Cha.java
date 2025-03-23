package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

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
        Direction.VERTICALS.stream()
                .filter(direction -> board.existsNextPoint(source, direction))
                .forEach(direction -> findCandidates(
                        board.getNextPoint(source, direction), board, direction,
                        candidates)
                );
        return candidates;
    }

    private void findCandidates(final Point currentPoint, final Board board, final Direction direction,
                                final List<Point> candidates) {
        candidates.add(currentPoint);
        if (!board.existsNextPoint(currentPoint, direction)) {
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
    public Team team() {
        return this.team;
    }
}
