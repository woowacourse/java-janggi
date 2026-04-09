package janggi.domain.piece;

import janggi.domain.Camp;

public enum PieceDisplayName {
    ADVISOR("士", "士"),
    CANNON("包", "包"),
    CHARIOT("車", "車"),
    ELEPHANT("象", "象"),
    GENERAL("楚", "漢"),
    HORSE("馬", "馬"),
    SOLIDER("卒", "兵");


    private final String choName;
    private final String hanName;

    PieceDisplayName(String choName, String hanName) {
        this.choName = choName;
        this.hanName = hanName;
    }

    public String findDisplayName(Camp camp) {
        if (camp.isCho()) {
            return choName;
        }
        return hanName;
    }

    public static boolean isGeneral(String displayName) {
        return GENERAL.choName.equals(displayName) || GENERAL.hanName.equals(displayName);
    }
}
