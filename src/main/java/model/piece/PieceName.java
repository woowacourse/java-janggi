package model.piece;

public enum PieceName {
    JANG("漢"),
    SA("士"),
    SANG("象"),
    MA("馬"),
    CHA("車"),
    PO("包"),
    BYEONG("兵");


    private final String name;

    PieceName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
