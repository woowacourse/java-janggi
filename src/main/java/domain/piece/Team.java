package domain.piece;

import domain.board.Palace;

public enum Team {
    HAN(Palace.HAN_PALACE),
    CHO(Palace.CHO_PALACE);

    private final Palace palace;

    Team(Palace palace) {
        this.palace = palace;
    }

    public Palace getPalace() {
        return palace;
    }
}
