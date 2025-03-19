package domain;

import domain.pattern.Pattern;
import java.util.List;

public abstract class Piece {
    protected int score;
    protected Side side;

    public Piece(int score, Side side) {
        this.score = score;
        this.side = side;
    }

    public abstract List<Pattern> findPath(Position beforePosition, Position afterPosition);

    public boolean isEmpty() {
        return false;
    }
}
