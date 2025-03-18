package janggi;

public enum Camp {

    HAN(false),
    CHU(true),
    ;

    private final boolean isBottom;

    Camp(boolean isBottom) {
        this.isBottom = isBottom;
    }

    public boolean isBottom() {
        return isBottom;
    }
}
