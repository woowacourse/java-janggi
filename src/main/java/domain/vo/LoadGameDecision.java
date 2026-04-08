package domain.vo;

public class LoadGameDecision {

    private static final String INVALID_INPUT_EXCEPTION = "y 또는 n만 입력해야 합니다.";

    private final String load;

    private LoadGameDecision(String load) {
        if (!load.equals("y") && !load.equals("n")) {
            throw new IllegalArgumentException(INVALID_INPUT_EXCEPTION);
        }
        this.load = load;
    }

    public static LoadGameDecision from(String input) {
        return new LoadGameDecision(input);
    }

    public boolean shouldLoad() {
        return load.equals("y");
    }
}
