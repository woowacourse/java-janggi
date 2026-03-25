package exception;

public final class Validator {
    public static int validateNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("번호를 입력해주세요.");
        }
    }
}
