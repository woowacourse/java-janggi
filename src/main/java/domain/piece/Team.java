package domain.piece;

public enum Team {

    CHO(true),
    HAN(false),
    NONE(false),
    ;

    private final boolean isCho;

    Team(boolean isCho) {
        this.isCho = isCho;
    }

    public boolean isCho() {
        return isCho;
    }
}
