package exception;

import java.util.List;

public final class Validator {
    public static int validateNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        }
    }

    public static void validateNumberInRange(int min, int max, int input) {
        if (input > max || input < min) {
            throw new IllegalArgumentException("범위 내의 숫자가 아닙니다.");
        }
    }

    public static void validateContainsNumber(int input, List<Integer> numbers) {
        if (!numbers.contains(input)) {
            throw new IllegalArgumentException("존재하지 않는 게임 방 번호입니다.");
        }
    }
}
