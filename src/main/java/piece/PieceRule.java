package piece;

import java.util.Objects;
import strategy.MoveStrategy;

public class PieceRule {

    private final MoveStrategy moveStrategy;
    private final PieceType pieceType;

    public PieceRule(MoveStrategy moveStrategy, PieceType pieceType) {
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceRule pieceRule = (PieceRule) o;
        return Objects.equals(moveStrategy, pieceRule.moveStrategy) && pieceType == pieceRule.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveStrategy, pieceType);
    }
}
