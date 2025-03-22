package janggi;

public enum Camp {

    HAN("한") {
        @Override
        public Camp reverse() {
            return CHU;
        }
    },
    CHU("초") {
        @Override
        public Camp reverse() {
            return HAN;
        }
    },
    NEUTRAL("") {
        @Override
        public Camp reverse() {
            return NEUTRAL;
        }
    },
    ;

    private final String name;

    Camp(String name) {
        this.name = name;
    }

    public abstract Camp reverse();

    public boolean isBottom() {
        return this == CHU;
    }

    public String getName() {
        return name;
    }
}
