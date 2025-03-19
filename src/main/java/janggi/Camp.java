package janggi;

public enum Camp {

    HAN(false, "한") {
        @Override
        public Camp reverse() {
            return CHU;
        }
    },
    CHU(true, "초") {
        @Override
        public Camp reverse() {
            return HAN;
        }
    },
    ;

    private final boolean isBottom;
    private final String name;

    Camp(boolean isBottom, String name) {
        this.isBottom = isBottom;
        this.name = name;
    }

    public abstract Camp reverse();

    public boolean isBottom() {
        return isBottom;
    }

    public String getName() {
        return name;
    }
}
