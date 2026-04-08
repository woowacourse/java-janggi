package parser;

import dto.InputMoveDto;
import dto.InputPointDto;

public class MoveInputParser {
    private MoveInputParser() {
    }

    public static InputMoveDto parse(String from, String to) {
        return new InputMoveDto(parsePoint(from), parsePoint(to));
    }

    private static InputPointDto parsePoint(String input) {
        try {
            String[] tokens = input.trim().split("\\s+");
            if (tokens.length != 2) {
                throw new IllegalArgumentException("좌표는 y x 형식이어야 합니다.");
            }
            int y = Integer.parseInt(tokens[0]);
            int x = Integer.parseInt(tokens[1]);
            return new InputPointDto(y, x);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }
}
