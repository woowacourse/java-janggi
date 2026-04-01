package domain.board;

public enum Column {
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
        return next >= 0 && next < Column.values().length;
    }

    public Column shift(int delta) {
        return Column.values()[this.ordinal() + delta];
    }

    public static Column toCol(char character) {
        String convertedCharacter = String.valueOf(character);
        return Column.valueOf(convertedCharacter.toUpperCase());
    }
}
