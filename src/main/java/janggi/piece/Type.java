package janggi.piece;

import janggi.util.ColorConvertor;

public enum Type {

    CANNON("포", "포", Point.SEVEN),
    CHARIOT("차", "차", Point.THIRTEEN),
    ELEPHANT("상", "상", Point.THREE),
    GENERAL("궁", "궁", Point.NONE),
    GUARD("사", "사", Point.THREE),
    HORSE("마", "마", Point.FIVE),
    SOLDIER("졸", "병", Point.TWO),
    EMPTY(" ", " ", Point.NONE),
    ;

    private final String choName;
    private final String hanName;
    private final Point point;

    Type(String choName, String hanName, Point point) {
        this.choName = choName;
        this.hanName = hanName;
        this.point = point;
    }

    public String getDisplayAttributes(Camp camp) {
        if (camp.isBottom()) {
            return ColorConvertor.convertToGreen(choName);
        }
        return ColorConvertor.convertToRed(hanName);
    }

    public Double getPoint() {
        return (double) point.getPoint();
    }
}
