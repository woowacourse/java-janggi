package domain.place.piece;

import java.util.Arrays;
import java.util.Objects;

public enum Side {
    CHO("CHO", 10, -1),
    HAN("HAN", 1, 1);

    private final String name;
    private final int startLine;
    private final int setupDirection; // 초기화용

    Side(String name, int startLine, int setupDirection) {
        this.name = name;
        this.startLine = startLine;
        this.setupDirection = setupDirection;
    }

    public String getName() {
        return name;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getSetupDirection() {
        return setupDirection;
    }

    public Side from(String side){
        return Arrays.stream(Side.values())
                .filter(side1 -> side1.name.equals(side))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 진영을 찾을 수 없습니다."));
    }

    public Side opposite() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

}
