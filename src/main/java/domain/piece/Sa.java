package domain.piece;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Point;
import java.util.Arrays;
import java.util.List;

public class Sa implements Piece {

    private static final List<Direction> SA_MOVABLE_DIRECTIONS = Arrays.stream(Direction.values()).toList();

    private final Team team;

    public Sa(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(final Point source, final Point destination, final Board board) {
        return findMovablePoints(source, board).contains(destination);
    }

    private List<Point> findMovablePoints(final Point point, final Board board) {
        return SA_MOVABLE_DIRECTIONS.stream()
                .filter(direction -> board.existsNextPoint(point, direction))
                .map(direction -> board.getNextPoint(point, direction))
                .filter(nextPoint -> !(board.existsPiece(nextPoint) && board.matchTeam(nextPoint, this.team)))
                .toList();
    }

    @Override
    public PieceType type() {
        return PieceType.SA;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
