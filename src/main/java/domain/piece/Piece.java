package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.state.PieceState;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected final int score;
    protected final Side side;
    protected Path path;
    protected PieceState state;
    private Piece targetPiece;

    public Piece(int score, Side side, Path path, PieceState state) {
        this.score = score;
        this.side = side;
        this.path = path;
        this.state = state;
    }

    public abstract PieceSymbol getPieceSymbol();

    public List<Pattern> findMovablePath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return state.findMovablePath(path, beforePosition, afterPosition);
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isSameSide(Side otherSide) {
        return this.side == otherSide;
    }

    public void capture(Piece targetPiece) {
        if (canCapture(targetPiece)) {
            targetPiece.beCaptured();
        }
    }

    public boolean canCapture(Piece targetPiece) {
        return !targetPiece.isEmpty();
    }

    public void beCaptured() {
        this.state = state.captured();
    }

    public boolean isGeneral() {
        return false;
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

    public List<JanggiPosition> getPositionsFromPatterns(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        List<Pattern> patterns = findMovablePath(beforePosition, afterPosition);
        List<Pattern> patternsWithoutDestination = patterns.subList(0, patterns.size() - 1);

        List<JanggiPosition> positions = new ArrayList<>();

        JanggiPosition newPosition = beforePosition;
        for (Pattern pattern : patternsWithoutDestination) {
            newPosition = newPosition.moveOnePosition(pattern);
            positions.add(newPosition);
        }
        return positions;
    }

    public void validateMove(List<Piece> hurdlePieces) {
        state.validateMove(hurdlePieces);
    }

    public int getScore() {
        return score;
    }
}
