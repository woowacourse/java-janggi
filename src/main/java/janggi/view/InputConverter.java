package janggi.view;

public class InputConverter {
    public static String extractLeftHorseSide(String input) {
        try {
            return input.substring(0, 2);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
    public static String extractRightHorseSide(String input) {
        try {
            return input.substring(2, 4);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
