package domain.piece.character;

public enum Team {

    CHO,
    HAN,
    ;

    public Team inverse() {
        if (this == CHO) {
            return HAN;
        }

        return CHO;
    }
}
