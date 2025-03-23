package janggi;

public enum Camp {

    HAN("한") {
        @Override
        public Camp nextTurn() {
            return CHU;
        }
    },
    CHU("초") {
        @Override
        public Camp nextTurn() {
            return HAN;
        }
    },
    ;

    private final String name;

    Camp(String name) {
        this.name = name;
    }

    public abstract Camp nextTurn();

    public boolean isStart() {
        return this == CHU;
    }

    public String getName() {
        return name;
    }
}
