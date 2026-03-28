package domain.piece;

public enum TeamColor {
    CHO,
    HAN;

    public String displayName() {
        if (this == CHO) {
            return "초";
        }
        return "한";
    }
}



