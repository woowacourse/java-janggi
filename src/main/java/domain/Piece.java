package domain;

import domain.pattern.Pattern;
import java.util.List;

public abstract class Piece {
    protected int score;
    protected Side side;
    protected PieceStatus status;

    public Piece(int score, Side side) {
        this.score = score;
        this.side = side;
        this.status = PieceStatus.ACTIVE;
    }

    public abstract List<Pattern> findPath(Position beforePosition, Position afterPosition);

    public boolean isEmpty() {
        return false;
    }

    public void capture() {
        this.status = PieceStatus.CAPTURED;
    }

    public PieceStatus getStatus() {
        return status;
    }
}
