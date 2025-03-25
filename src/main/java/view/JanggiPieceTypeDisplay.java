package view;

import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import java.util.HashMap;
import java.util.Map;

public enum JanggiPieceTypeDisplay {

    KING("궁", JanggiPieceType.KING),
    HORSE("마", JanggiPieceType.HORSE),
    ADVISOR("사", JanggiPieceType.ADVISOR),
    ELEPHANT("상", JanggiPieceType.ELEPHANT),
    SOLDIER_OF_CHO("졸", JanggiPieceType.SOLDIER),
    SOLDIER_OF_HAN("병", JanggiPieceType.SOLDIER),
    CHARIOT("차", JanggiPieceType.CHARIOT),
    CANNON("포", JanggiPieceType.CANNON),
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

    public static String getJanggiTypeDisplay(JanggiPieceType type, JanggiSide side) {
        if (type == JanggiPieceType.SOLDIER && side == JanggiSide.CHO) {
            return SOLDIER_OF_CHO.display;
        }
        if (type == JanggiPieceType.SOLDIER && side == JanggiSide.HAN) {
            return SOLDIER_OF_HAN.display;
        }
        return JANGGI_PIECE_TYPE_DISPLAY.get(type);
    }
}
