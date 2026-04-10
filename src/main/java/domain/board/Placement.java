package domain.board;

import java.util.Arrays;

public enum Placement {

    INNER_ELEPHANT(1, new InnerElephantSetup()),
    OUTER_ELEPHANT(2, new OuterElephantSetup()),
    RIGHT_ELEPHANT(3, new RightElephantSetup()),
    LEFT_ELEPHANT(4, new LeftElephantSetup());

    private final int code;
    private final Setup setup;

    Placement(int code, Setup setup) {
        this.code = code;
        this.setup = setup;
    }

    public static Placement from(int code) {
        return Arrays.stream(values())
                .filter(placement -> placement.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 코드값 입니다."));
    }

    public Setup getSetup() {
        return setup;
    }
}
