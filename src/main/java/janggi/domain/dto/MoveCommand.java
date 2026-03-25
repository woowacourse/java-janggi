package janggi.domain.dto;

import janggi.domain.vo.Position;

public class MoveCommand {
    private final Position from;
    private final Position to;

    private MoveCommand(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public static MoveCommand from(int[] input) {
        Position from = new Position(input[0], input[1]);
        Position to = new Position(input[2], input[3]);

        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 제자리 이동 ㄴㄴ");
        }

        return new MoveCommand(from, to);
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }
}
