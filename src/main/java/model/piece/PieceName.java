package model.piece;

public enum PieceName {
    JANG("漢"),
    SA("士"),
    SANG("象"),
    MA("馬"),
    CHA("車"),
    PHO("包"),
    BYEONG("兵");

    
    public String getName() {
        return name;
    }

    private final String name;

    PieceName(String name) {
        this.name = name;
    }
}
