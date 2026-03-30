package view;

import board.InnerSangSetup;
import board.LeftSangSetup;
import board.OuterSangSetup;
import board.RightSangSetup;
import board.SangSetup;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum SangSetupType {

    LEFT_SANG_SETUP(1, "왼상차림", new LeftSangSetup()),
    RIGHT_SANG_SETUP(2, "오른상차림", new RightSangSetup()),
    INNER_SANG_SETUP(3, "안상차림", new InnerSangSetup()),
    OUTER_SANG_SETUP(4, "바깥상차림", new OuterSangSetup());

    private final int inputNumber;
    private final String displayName;
    private final SangSetup sangSetup;

    SangSetupType(int inputNumber, String displayName, SangSetup sangSetup) {
        this.inputNumber = inputNumber;
        this.displayName = displayName;
        this.sangSetup = sangSetup;
    }

    public static SangSetup from(int inputNumber) {
        return findByInputNumber(inputNumber).sangSetup;
    }

    public static SangSetupType findByInputNumber(int inputNumber) {
        return Arrays.stream(values())
            .filter(type -> type.inputNumber == inputNumber)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 상차림 번호입니다."));
    }

    public static String convertDisplayFormat() {
        return Arrays.stream(SangSetupType.values())
            .map(type -> type.inputNumber + ". " + type.displayName)
            .collect(Collectors.joining("\n"));
    }
}