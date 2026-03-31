package domain.board;

public enum Col {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I;

    public boolean canShift(int delta) {
        int next = this.ordinal() + delta;
        return next >= 0 && next < Col.values().length;
    }

    public Col shift(int delta) {
        return Col.values()[this.ordinal() + delta];
    }

    public static Col toCol(char character) {
        String convertedCharacter = String.valueOf(character);
        return Col.valueOf(convertedCharacter.toUpperCase());
    }
}
