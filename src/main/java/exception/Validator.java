package exception;

public final class Validator {
    public static int validateNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        }
    }

    public static void validateNumberInRange(int min, int max, int input) {
        if (input>max || input<min) {
            throw new IllegalArgumentException("범위 내의 숫자가 아닙니다.");
        }
    }

    public static void validateLength(int size, int input) {
        if (input!=size) {
            throw new IllegalArgumentException(String.format("%d개의 숫자를 입력해주세요.",size));
        }
    }

    public static boolean validateYesOrNo(String input) {
        if (input.equals("y") || input.isBlank()) {
            return true;
        }
        if  (input.equals("n")) {
            return false;
        }
        throw new IllegalArgumentException("입력이 올바르지 않습니다.");
    }

    public static void validateDataExist(boolean isDataExist, String message) {
        if (!isDataExist) {
            throw new IllegalArgumentException(message);
        }
    }
}
