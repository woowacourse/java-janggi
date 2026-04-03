package domain.piece;

import domain.player.Team;
import java.util.function.Function;

public enum PieceType {
    CHA("CH", "車", "車", Cha::new),
    MA("MA", "馬", "馬", Ma::new),
    SA("SA", "士", "士", Sa::new),
    SANG("SD", "象", "象", Sang::new),
    JANG("JA", "楚", "漢", Jang::new),
    PO("PO", "包", "包", Po::new),
    JOL("ZO", "卒", "兵", Jol::new),
    NONE("  ", "＋", "＋", team -> new None());

    private final String symbol;
    private final String choDisplayName;
    private final String hanDisplayName;
    private final Function<Team, Piece> constructor;

    PieceType(String symbol, String choDisplayName, String hanDisplayName, Function<Team, Piece> constructor) {
        this.symbol = symbol;
        this.choDisplayName = choDisplayName;
        this.hanDisplayName = hanDisplayName;
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

    public Piece create(Team team) {
        return constructor.apply(team);
    }
}
