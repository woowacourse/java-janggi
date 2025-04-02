package janggi.domain.piece;

import janggi.util.ColorConvertor;
import java.util.function.Function;

public enum Type {

    CANNON("포", Point.SEVEN, Cannon::from),
    CHARIOT("차", Point.THIRTEEN, Chariot::from),
    ELEPHANT("상", Point.THREE, Elephant::from),
    GENERAL("궁", Point.NONE, General::from),
    GUARD("사", Point.THREE, Guard::from),
    HORSE("마", Point.FIVE, Horse::from),
    SOLDIER("졸병", Point.TWO, Soldier::from),
    EMPTY(" ", Point.NONE, camp -> Empty.getInstance()),
    ;

    private final String name;
    private final Point point;
    private final Function<Camp, Piece> creator;

    Type(final String name, final Point point, final Function<Camp, Piece> creator) {
        this.name = name;
        this.point = point;
        this.creator = creator;
    }

    public String getDisplayAttributes(Camp camp) {
        String displayName = getDisplayName(camp);
        if (camp.isBottom()) {
            return ColorConvertor.convertToGreen(displayName);
        }
        return ColorConvertor.convertToRed(displayName);
    }

    private String getDisplayName(Camp camp) {
        if (this == SOLDIER) {
            return getSoldierDisplayName(camp);
        }
        return name;
    }

    private String getSoldierDisplayName(Camp camp) {
        if (camp.isBottom()) {
            return name.substring(0, 1);
        }
        return name.substring(1, 2);
    }

    public Double getPoint() {
        return (double) point.getPoint();
    }

    public Piece createPiece(Camp camp) {
        return creator.apply(camp);
    }
}
