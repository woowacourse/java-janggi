package janggi.game;

import janggi.rule.CampType;
import janggi.value.Position;
import java.util.Objects;

public record MovePieceCommand(int commandId, CampType campType, Position targetPiecePosition, Position destination) {

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MovePieceCommand command = (MovePieceCommand) object;
        return commandId == command.commandId && campType == command.campType && Objects.equals(
                targetPiecePosition, command.targetPiecePosition) && Objects.equals(destination,
                command.destination);
    }

}
