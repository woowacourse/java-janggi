package domain.piece;

import domain.player.Team;
import java.util.function.Function;

public enum PieceType {
    CHA("CH", "車", "車", 13.0, Cha::new),
    MA("MA", "馬", "馬", 5.0, Ma::new),
    SA("SA", "士", "士", 3.0, Sa::new),
    SANG("SD", "象", "象", 3.0, Sang::new),
    JANG("JA", "楚", "漢", 0.0, Jang::new),
    PO("PO", "包", "包", 7.0, Po::new),
    JOL("ZO", "卒", "兵", 2.0, Jol::new),
    NONE("  ", "＋", "＋", 0.0, team -> new None());

    private final String symbol;
    private final String choDisplayName;
    private final String hanDisplayName;
    private final double point;
    private final Function<Team, Piece> constructor;

    PieceType(String symbol, String choDisplayName, String hanDisplayName, double point,
              Function<Team, Piece> constructor) {
        this.symbol = symbol;
        this.choDisplayName = choDisplayName;
        this.hanDisplayName = hanDisplayName;
        this.point = point;
        this.constructor = constructor;
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

    public double getPoint() {
        return point;
    }

    public Piece create(Team team) {
        return constructor.apply(team);
    }
}
