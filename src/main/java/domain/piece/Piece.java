package domain.piece;

import domain.board.Board;
import domain.board.Point;
import java.util.List;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean canMove(final Point source, final Point destination, final Board board) {
        return findMovablePoints(source, board).contains(destination);
    }

    protected abstract List<Point> findMovablePoints(Point point, Board board);

    public Team team() {
        return team;
    }

    public abstract PieceType type();

    public abstract int score();
}
