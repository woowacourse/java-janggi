package domain.piece;

import domain.player.Team;

public enum PieceType {
    CHA("CH", "車", "車"),
    MA("MA", "馬", "馬"),
    SA("SA", "士", "士"),
    SANG("SD", "象", "象"),
    JANG("JA", "楚", "漢"),
    PO("PO", "包", "包"),
    JOL("ZO", "卒", "兵"),
    NONE("  ", "＋", "＋");

    private final String symbol;
    private final String choDisplayName;
    private final String hanDisplayName;

    PieceType(String symbol, String choDisplayName, String hanDisplayName) {
        this.symbol = symbol;
        this.choDisplayName = choDisplayName;
        this.hanDisplayName = hanDisplayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDisplayName(Team team) {
        if (team.isCho()) {
            return choDisplayName;
        }
        return hanDisplayName;
    }
}
