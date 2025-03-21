package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.state.PieceState;
import java.util.List;

public abstract class Piece {
    protected final int score;
    protected final Side side;
    protected Path path;
    protected PieceState state;

    public Piece(int score, Side side, Path path, PieceState state) {
        this.score = score;
        this.side = side;
        this.path = path;
        this.state = state;
    }

    public List<Pattern> findPath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return state.findMovablePath(path, beforePosition, afterPosition);
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isSameSide(Side otherSide) {
        return this.side == otherSide;
    }

    public void captureIfNotMySide(Piece piece) {
        if (piece.side == this.side) {
            return;
        }
        this.state = state.captured();
    }

    public Side getSide() {
        return side;
    }

    public Path getPath() {
        return path;
    }

    public void updateState() {
        this.state = state.updateState();
    }

    public PieceState getState() {
        return state;
    }
}
