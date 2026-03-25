package domain.vo;

import java.util.Arrays;

public enum Arrangement {
    //TODO:네이밍 다시 적기
    MASANGMASANG("1"),
    MASANGSANGMA("2"),
    SANGMAMASANG("3"),
    SANGMASANGMA("4");

    private final String value;

    Arrangement(String value) {
        this.value = value;
    }

    public static Arrangement toArrangement(String setupCommand) {
        return Arrays.stream(values())
                .filter(arrangement -> arrangement.value.equals(setupCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("1~4까지의 숫자만 입력 가능합니다."));
    }

}
