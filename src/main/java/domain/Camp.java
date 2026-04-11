package domain;

public enum Camp {
    HAN("한나라"),
    CHO("초나라"),
    NONE("");

    private final String displayName;

    Camp(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

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
