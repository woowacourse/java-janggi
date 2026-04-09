package presentation;

import domain.vo.Position;

public class PositionCommand {

    private static final String POSITION_PATTERN = "^\\d+\\s+\\d+$";

    private final Position position;

    private PositionCommand(final Position position) {
        this.position = position;
    }

    public static PositionCommand from(final String input) {
        validate(input);

        String[] tokens = input.split(" ");
        return new PositionCommand(
                Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]))
        );
    }

    private static void validate(final String input) {
        if (!input.matches(POSITION_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다. '숫자 공백 숫자' 형식이어야 합니다.");
        }
    }

    public Position toPosition() {
        return position;
    }
}
