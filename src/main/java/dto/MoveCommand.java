package dto;

import domain.board.Intersection;

public record MoveCommand(Type type, Intersection selectedToMove) {

    public static MoveCommand from(String rawMoveCommand) {
        if (rawMoveCommand.equalsIgnoreCase("exit")) {
            return new MoveCommand(Type.EXIT, null);
        }

        try {
            Intersection parsed = Intersection.parse(rawMoveCommand);
            return new MoveCommand(Type.MOVE, parsed);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public boolean isExit() {
        return type == Type.EXIT;
    }

    enum Type {
        EXIT, MOVE
    }
}
