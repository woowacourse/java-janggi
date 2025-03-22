package view;

import domain.piece.JanggiPieceType;
import java.util.HashMap;
import java.util.Map;

public enum JanggiPieceTypeDisplay {

    궁("궁", JanggiPieceType.궁),
    마("마", JanggiPieceType.마),
    사("사", JanggiPieceType.사),
    상("상", JanggiPieceType.상),
    졸("졸", JanggiPieceType.졸),
    병("병", JanggiPieceType.병),
    차("차", JanggiPieceType.차),
    포("포", JanggiPieceType.포),
    EMPTY("ㅡ", JanggiPieceType.EMPTY);

    private static final Map<JanggiPieceType, String> JANGGI_PIECE_TYPE_DISPLAY;

    static {
        JANGGI_PIECE_TYPE_DISPLAY = new HashMap<>();
        for (JanggiPieceTypeDisplay display : JanggiPieceTypeDisplay.values()) {
            JANGGI_PIECE_TYPE_DISPLAY.put(display.type, display.display);
        }
    }

    private final String display;
    private final JanggiPieceType type;

    JanggiPieceTypeDisplay(String display, JanggiPieceType type) {
        this.display = display;
        this.type = type;
    }

    public static String getJanggiTypeDisplay(JanggiPieceType type) {
        return JANGGI_PIECE_TYPE_DISPLAY.get(type);
    }
}
