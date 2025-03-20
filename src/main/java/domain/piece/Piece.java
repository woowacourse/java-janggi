package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import java.util.List;

public abstract class Piece {
    protected int score;
    protected Side side;
    protected PieceStatus status;
    protected Path path;

    public Piece(int score, Side side, Path path) {
        this.score = score;
        this.side = side;
        this.status = PieceStatus.ACTIVE;
        this.path = path;
    }

    public List<Pattern> findPath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return getPath().getPath(beforePosition, afterPosition);
    }

    public boolean isEmpty() {
        return false;
    }

    public void capture() {
        this.status = PieceStatus.CAPTURED;
    }

    public Side getSide() {
        return side;
    }

    public PieceStatus getStatus() {
        return status;
    }

    public Path getPath() {
        return path;
    }
}
