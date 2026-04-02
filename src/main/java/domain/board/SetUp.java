package domain.board;

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;

import domain.piece.PieceType;
import java.util.List;

public enum SetUp {
    LEFT_ELEPHANT(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE)),
    RIGHT_ELEPHANT(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT)),
    INNER_ELEPHANT(List.of(HORSE, ELEPHANT, ELEPHANT, HORSE)),
    OUTER_ELEPHANT(List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));

    private final List<PieceType> placeOrder;

    SetUp(List<PieceType> placeOrder) {
        this.placeOrder = List.copyOf(placeOrder);
    }

    public List<PieceType> placeOrder() {
        return placeOrder;
    }
}
