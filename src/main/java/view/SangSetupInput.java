package view;

import board.SangSetupType;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum SangSetupInput {

    LEFT_SANG_SETUP(1, "왼상차림", SangSetupType.LEFT_SANG_SETUP),
    RIGHT_SANG_SETUP(2, "오른상차림", SangSetupType.RIGHT_SANG_SETUP),
    INNER_SANG_SETUP(3, "안상차림", SangSetupType.INNER_SANG_SETUP),
    OUTER_SANG_SETUP(4, "바깥상차림", SangSetupType.OUTER_SANG_SETUP),
    ;

    private final int inputNumber;
    private final String displayName;
    private final SangSetupType sangSetupType;

    SangSetupInput(final int inputNumber, final String displayName, final SangSetupType sangSetupType) {
        this.inputNumber = inputNumber;
        this.displayName = displayName;
        this.sangSetupType = sangSetupType;
    }

    public static SangSetupType from(final int inputNumber) {
        return findByInputNumber(inputNumber).sangSetupType;
    }

    public static SangSetupInput findByInputNumber(final int inputNumber) {
        return Arrays.stream(values())
            .filter(type -> type.inputNumber == inputNumber)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 상차림 번호입니다."));
    }

    public static String convertDisplayFormat() {
        return Arrays.stream(SangSetupInput.values())
            .map(type -> type.inputNumber + ". " + type.displayName)
            .collect(Collectors.joining("\n"));
    }
}