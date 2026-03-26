package janggi.domain;

import java.util.List;

public enum PieceType {
    KING("왕", List.of(5),9),
    SA("사",List.of(4,6),10),
    SANG("상",List.of(3,7),10),
    MA("마",List.of(2,8),10),
    CHA("차",List.of(1,9),10),
    PO("포",List.of(2,8),8),
    ZOL("졸",List.of(1,3,5,7,9),7);

    private final String name;
    private final List<Integer> xPositions;
    private final int yPosition;

    PieceType(String name, List<Integer> xPositions, int yPosition) {
        this.name = name;
        this.xPositions = xPositions;
        this.yPosition = yPosition;
    }

    public String getName(){
        return name;
    }


    public List<Integer> getXPositions() {
        return xPositions;
    }

    public int getYPosition() {
        return yPosition;
    }
}
