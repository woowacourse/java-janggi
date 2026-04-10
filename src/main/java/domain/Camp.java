package domain;

public enum Camp {
    HAN,
    CHO,
    NONE;

    public Camp turnCamp() {
        if (this == HAN) {
            return CHO;
        }
        if (this == CHO) {
            return HAN;
        }
        throw new IllegalStateException("NONE 진영은 다음 턴이 없습니다.");
    }
}
