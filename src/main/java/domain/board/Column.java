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

    public static Column toColumn(char character) {
        String convertedCharacter = checkExistingColumn(character);
        return Column.valueOf(convertedCharacter.toUpperCase());
    }

    private static String checkExistingColumn(char character) {
        try {
            return String.valueOf(character);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("[ERROR] 좌표 형식이 틀렸습니다.");
        }
    }
}
