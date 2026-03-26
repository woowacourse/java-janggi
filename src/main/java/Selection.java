import java.util.Arrays;

public enum Selection {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    ;
    
    private final String input;

    Selection(String input) {
        this.input = input;
    }

    public static Selection from(String input) {
        return Arrays.stream(values())
                .filter(selection -> selection.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 입력이 아닙니다."));
    }
}
