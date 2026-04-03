package domain.direction;

public record MoveAmount(
        int amount
) {

    private static final int MINIMUM_AMOUNT = 0;

    public MoveAmount {
        if (amount < MINIMUM_AMOUNT) {
            throw new IllegalArgumentException("이동 거리는 " + MINIMUM_AMOUNT + " 이상이어야 합니다.");
        }
    }
}
