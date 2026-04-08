package view.dto;

import domain.position.Position;

public record PositionInput(PositionCommand command, Position position) {

    public static PositionInput pausing() {
        return new PositionInput(PositionCommand.PAUSE, null);
    }

    public static PositionInput finishing() {
        return new PositionInput(PositionCommand.FINISH, null);
    }

    public static PositionInput of(Position position) {
        return new PositionInput(PositionCommand.MOVE, position);
    }

    public boolean pause() {
        return command == PositionCommand.PAUSE;
    }

    public boolean finish() {
        return command == PositionCommand.FINISH;
    }
}
