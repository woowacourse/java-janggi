package janggi.domain.piece;

import java.util.Objects;

public class Piece {

    private final PieceRule pieceRule;
    private final Camp camp;

    public Piece(PieceRule pieceRule, Camp camp) {
        this.pieceRule = pieceRule;
        this.camp = camp;
    }

    public boolean isSamePieceRule(PieceRule pieceRule) {
        return this.pieceRule == pieceRule;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceRule == piece.pieceRule && camp == piece.camp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceRule, camp);
    }
}
