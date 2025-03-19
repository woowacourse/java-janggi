package piece;

import java.util.Objects;
import strategy.MoveStrategy;

public class Piece {
    private final Position position;

    private final PieceRule pieceRule;

    public Piece(Position position, MoveStrategy moveStrategy, PieceType pieceType) {
        this.position = position;
        this.pieceRule = new PieceRule(moveStrategy, pieceType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return Objects.equals(position, piece.position) && Objects.equals(pieceRule, piece.pieceRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, pieceRule);
    }
}
