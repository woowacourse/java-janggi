package view;

public enum GameContinueOption {

    Y,
    N;

    public static GameContinueOption parseToOption(String optionInput) {
        if (optionInput.equals("y")) {
            return Y;
        }
        if (optionInput.equals("n")) {
            return N;
        }
        throw new IllegalArgumentException("해당 옵션은 존재하지 않습니다.");
    }
}
