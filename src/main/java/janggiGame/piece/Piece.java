package janggiGame.piece;

import janggiGame.position.Position;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    protected final Dynasty dynasty;
    protected final Type type;

    public Piece(Dynasty dynasty, Type type) {
        this.dynasty = dynasty;
        this.type = type;
    }

    protected void validateSameDynasty(Piece piece) {
        if (piece != null && piece.dynasty == this.dynasty) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    public Type getType() {
        return type;
    }

    public Integer getPoint() {
        return type.getPoint();
    }

    public abstract List<Position> getRoute(Position origin, Position destination);

    public abstract void validateMove(Map<Position, Piece> routesWithPiece, Piece destinationPiece);

    protected abstract void validateRoute(int dx, int dy);
}
