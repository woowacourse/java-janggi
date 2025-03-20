package janggiGame.piece;

import janggiGame.board.Dot;

import java.util.List;
import java.util.Map;

public abstract class Piece {
    protected final Dynasty dynasty;

    public Piece(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    public void validateSameDynasty(Piece piece) {
        if (piece != null && piece.dynasty == this.dynasty) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    public abstract List<Dot> getRoute(Dot origin, Dot destination);

    public abstract void validateRoute(int dx, int dy);

    public abstract void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece);

    public abstract String getName();
}