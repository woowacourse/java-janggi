package model;

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
