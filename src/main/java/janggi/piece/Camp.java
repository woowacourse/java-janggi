package janggi.piece;

public enum Camp {

    HAN("한"),
    CHO("초"),
    NONE(" "),
    ;

    private final String name;

    Camp(String name) {
        this.name = name;
    }

    public boolean isBottom() {
        return this == CHO;
    }

    public Camp opposite() {
        if (this == HAN) {
            return CHO;
        }
        if (this == CHO) {
            return HAN;
        }
        return NONE;
    }

    public String getName() {
        return name;
    }
}
