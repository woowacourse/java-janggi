package view;

import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import java.util.HashMap;
import java.util.Map;

public enum JanggiSideDisplay {

    CHO("초", JanggiSide.CHO),
    HAN("한", JanggiSide.HAN),
    EMPTY(" ", JanggiSide.NONE);

    private static final Map<JanggiSide, String> JANGGI_SIDE_DISPLAY;

    static {
        JANGGI_SIDE_DISPLAY = new HashMap<>();
        for (JanggiSideDisplay display : JanggiSideDisplay.values()) {
            JANGGI_SIDE_DISPLAY.put(display.side, display.display);
        }
    }

    private final String display;
    private final JanggiSide side;

    JanggiSideDisplay(String display, JanggiSide side) {
        this.display = display;
        this.side = side;
    }

    public static String getJanggiSideDisplay(JanggiSide side) {
        return JANGGI_SIDE_DISPLAY.get(side);
    }
}
