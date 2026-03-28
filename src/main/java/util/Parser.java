package util;

import domain.PieceType;
import domain.Position;
import dto.MoveCommand;

public class Parser {
    public static MoveCommand parse(String command) {
        String[] split = command.split(" ");
        validateCommand(split);
        PieceType type = PieceType.fromKoreanName(split[0]);

        String[] positionFrom = split[1].split(",");
        Position from = Position.from(Integer.parseInt(positionFrom[0]), Integer.parseInt(positionFrom[1]));

        String[] positionTo = split[3].split(",");
        Position to = Position.from(Integer.parseInt(positionTo[0]), Integer.parseInt(positionTo[1]));

        return MoveCommand.of(type, from, to);
    }

    private static void validateCommand(String[] split) {
        if (split.length != 4) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }
}
