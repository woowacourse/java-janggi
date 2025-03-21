package domain.piece;

import java.util.List;

public enum JanggiSide {
    CHO,
    HAN,
    NONE;

    private static final List<JanggiSide> VALID_SIDES = List.of(CHO, HAN);

    public static List<JanggiSide> getValidSides() {
        return VALID_SIDES;
    }
}
