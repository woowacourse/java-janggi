package model;

import java.util.Arrays;

public enum Team {
    HAN(0, 0, 1, 1),
    CHO(0, 9, 1, -1),
    ;

    private final int baseX;
    private final int baseY;
    private final int dx;
    private final int dy;

    Team(int baseX, int baseY, int dx, int dy) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.dx = dx;
        this.dy = dy;
    }

    public static Team from(String name) {
        return Arrays.stream(values())
            .filter(team -> team.name().equals(name))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 팀입니다."));
    }

    public Team nextTurn() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public int onBaseX(int x) {
        return baseX + dx * x;
    }

    public int onBaseY(int y) {
        return baseY + dy * y;
    }
}
