package domain.vo;

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
        String convertedCharacter = checkExistingColumn(character);
        return Col.valueOf(convertedCharacter.toUpperCase());
    }

    private static String checkExistingColumn(char character) {
        try {
            return String.valueOf(character);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("[ERROR] 좌표 형식이 틀렸습니다.");
        }
    }
}
