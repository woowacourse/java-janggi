package domain;

public enum Team {
    HAN(false),
    CHO(true);

    private final boolean isFirst;

    Team(final boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isFirst() {
        return isFirst;
    }
}
