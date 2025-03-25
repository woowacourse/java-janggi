package janggi.piece;

import janggi.util.ColorConvertor;

public enum Type {

    CANNON("포", "포"),
    CHARIOT("차", "차"),
    ELEPHANT("상", "상"),
    GENERAL("궁", "궁"),
    GUARD("사", "사"),
    HORSE("마", "마"),
    SOLDIER("졸", "병"),
    EMPTY(" ", " "),
    ;

    private final String chuName;
    private final String hanName;

    Type(String chuName, String hanName) {
        this.chuName = chuName;
        this.hanName = hanName;
    }

    public String getDisplayAttributes(Camp camp) {
        if (camp.isBottom()) {
            return ColorConvertor.convertToGreen(chuName);
        }
        return ColorConvertor.convertToRed(hanName);
    }
}
