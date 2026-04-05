package domain.board;

import domain.piece.PieceType;
import java.util.List;

public enum Formation {
    LEFT_ELEPHANT(
            List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)
    ),
    RIGHT_ELEPHANT(
            List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)
    ),
    OUTER_ELEPHANT(
            List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT)
    ),
    INNER_ELEPHANT(
            List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)
    ),
    ;

    private final List<PieceType> orders;

    Formation(List<PieceType> orders) {
        this.orders = List.copyOf(orders);
    }

    public List<PieceType> getOrders() {
        return orders;
    }
}
