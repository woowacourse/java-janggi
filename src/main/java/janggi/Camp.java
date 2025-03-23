package janggi;

public enum Camp {

    HAN("한") {
        @Override
        public Camp opposite() {
            return CHU;
        }
    },
    CHU("초") {
        @Override
        public Camp opposite() {
            return HAN;
        }
    },
    EMPTY(" ") {
        @Override
        public Camp opposite() {
            return EMPTY;
        }
    },
    ;

    private final String name;

    Camp(String name) {
        this.name = name;
    }

    public abstract Camp opposite();

    public boolean isBottom() {
        return this == CHU;
    }

    public String getName() {
        return name;
    }
}
