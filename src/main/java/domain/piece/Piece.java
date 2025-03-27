package domain.piece;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.board.PieceVisibleBoard;
import domain.point.Point;
import java.util.List;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean canMove(final Point source, final Point destination, final PieceVisibleBoard board) {
        List<Point> candidates = findMovablePoints(source, board);
        if (isOnlyMovableInPalace()) {
            candidates = candidates.stream()
                    .filter(Point::isInPalace)
                    .toList();
        }
        return candidates.contains(destination);
    }

    protected abstract List<Point> findMovablePoints(Point point, PieceVisibleBoard board);

    public abstract boolean isOnlyMovableInPalace();

    public Team team() {
        return team;
    }

    public abstract PieceType type();

    public abstract int score();
}
