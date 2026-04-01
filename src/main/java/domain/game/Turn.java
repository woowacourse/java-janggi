package domain.game;

import domain.piece.Team;

public enum Turn {

    CHO(true, "초"),
    HAN(false, "한");

    private final boolean isCho;
    private final String name;

    Turn(boolean isCho, String name) {
        this.isCho = isCho;
        this.name = name;
    }

    public boolean isCho() {
        return isCho;
    }

    public String getName() {
        return name;
    }

    public Team team() {
        if (this.isCho == CHO.isCho()) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public Turn reverse() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
