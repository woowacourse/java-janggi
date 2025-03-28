package domain.piece;

public enum Team {

    HAN,
    CHO,
    ;

    public Team inverse() {
        if (this == CHO) {
            return HAN;
        }

        return CHO;
    }
}
