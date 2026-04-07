package janggi.domain.piece;

public enum Team {
    CHO(),
    HAN(),
    NONE();

    public Team anotherTeam() {
        if (this == CHO) {
            return HAN;
        }

        if (this == HAN) {
            return CHO;
        }

        throw new IllegalArgumentException("CHO 또는 HAN의 진영을 입력해야 합니다.");
    }
}
