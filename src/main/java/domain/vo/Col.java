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
