package dto;

import domain.board.Intersection;
import java.util.Optional;

public record MoveCommand(Type type, Optional<Intersection> selectedToMove) {

    public static MoveCommand from(String rawMoveCommand) {
        if (rawMoveCommand.equalsIgnoreCase("exit")) {
            return new MoveCommand(Type.EXIT, Optional.empty());
        }

        Intersection parsed = Intersection.parse(rawMoveCommand);
        return new MoveCommand(Type.MOVE, Optional.of(parsed));
    }

    public boolean isExit() {
        return type == Type.EXIT;
    }

    enum Type {
        EXIT, MOVE
    }
}
