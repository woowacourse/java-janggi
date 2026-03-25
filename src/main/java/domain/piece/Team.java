package domain.piece;

public enum Team {

    CHO(true),
    HAN(false),
    ;

    private final boolean isFirst;

    Team(boolean isFirst) {
        this.isFirst = isFirst;
    }
}
