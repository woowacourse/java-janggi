package janggi.domain.board;

import java.util.Arrays;

public enum BoardOrder {

    ELEPHANT_HORSE_ELEPHANT_HORSE,
    HORSE_ELEPHANT_HORSE_ELEPHANT,
    ELEPHANT_HORSE_HORSE_ELEPHANT,
    HORSE_ELEPHANT_ELEPHANT_HORSE;

    public static BoardOrder from(final int inputOrder) {
        return Arrays.stream(BoardOrder.values())
                .filter(order -> order.ordinal() + 1 == inputOrder)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 입력입니다."));
    }
}
