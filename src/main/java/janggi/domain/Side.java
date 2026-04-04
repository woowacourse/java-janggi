package janggi.domain;

public enum Side {

    HAN("한"),
    CHO("초"),
    ;

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public Side switchSide() {
        if (this == HAN) {
            return CHO;
        }
        if (this == CHO) {
            return HAN;
        }
        throw new UnsupportedOperationException("진영이 존재하지 않아 진영을 반전시킬 수 없습니다.");
    }

    public String getName() {
        return name;
    }
}
