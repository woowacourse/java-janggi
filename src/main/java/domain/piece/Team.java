package domain.piece;

public enum Team {

    CHO(true),
    HAN(false),
    ;

    private final boolean isCho;

    Team(boolean isCho) {
        this.isCho = isCho;
    }

    public boolean isCho() {
        return isCho;
    }
}
