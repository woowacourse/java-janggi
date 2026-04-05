package janggi.domain;

public enum Side {

    HAN("한") {
        @Override
        public Side switchSide() {
            return CHO;
        }
    },
    CHO("초") {
        @Override
        public Side switchSide() {
            return HAN;
        }
    },
    ;

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public abstract Side switchSide();

    public String getName() {
        return name;
    }
}
