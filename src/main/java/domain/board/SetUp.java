package domain.board;

import domain.piece.PieceType;

import java.util.Arrays;
import java.util.List;

import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.ELEPHANT;

public enum SetUp {
    LEFT_ELEPHANT(1, List.of(ELEPHANT, HORSE, ELEPHANT, HORSE)),
    RIGHT_ELEPHANT(2, List.of(HORSE, ELEPHANT, HORSE, ELEPHANT)),
    INNER_ELEPHANT(3, List.of(HORSE, ELEPHANT, ELEPHANT, HORSE)),
    OUTER_ELEPHANT(4, List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));

    private final int setUpNumber;
    private final List<PieceType> placeOrder;

    SetUp(int setUpNumber, List<PieceType> types) {
        this.setUpNumber = setUpNumber;
        this.placeOrder = List.copyOf(types);
    }

    public static List<PieceType> from(int input) {
        return Arrays.stream(values())
                .filter(setUp -> setUp.setUpNumber == input)
                .map(setUp -> setUp.placeOrder)
                .findFirst()
                .orElseThrow();
    }
}
