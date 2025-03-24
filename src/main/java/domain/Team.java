package domain;

import execptions.JanggiArgumentException;
import java.util.EnumSet;

public enum Team {
    HAN(false, 9),
    CHO(true, 0),
    NONE(false, -1);

    private final boolean isFirst;
    private final int initialRow;

    Team(final boolean isFirst, int initialRow) {
        this.isFirst = isFirst;
        this.initialRow = initialRow;
    }

    public int calculateRowForPiece(int row) {
        if (this.equals(HAN)) {
            return getInitialRow() - row;
        }
        if (this.equals(CHO)) {
            return getInitialRow() + row;
        }
        throw new JanggiArgumentException("팀 정보가 없습니다.");
    }

    public boolean isFirst() {
        return isFirst;
    }

    public static EnumSet<Team> getActualTeams() {
        return EnumSet.complementOf(EnumSet.of(Team.NONE));
    }

    public int getInitialRow() {
        return initialRow;
    }
}
