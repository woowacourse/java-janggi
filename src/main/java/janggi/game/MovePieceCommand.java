package janggi.game;

import janggi.rule.CampType;
import janggi.value.Position;
import java.util.Objects;

public final class MovePieceCommand {

    private final int commandId;
    private final CampType campType;
    private final Position targetPiecePosition;
    private final Position destination;

    public MovePieceCommand(int commandId, CampType campType, Position targetPiecePosition, Position destination) {
        this.commandId = commandId;
        this.campType = campType;
        this.targetPiecePosition = targetPiecePosition;
        this.destination = destination;
    }

    public int getCommandId() {
        return commandId;
    }

    public CampType getCampType() {
        return campType;
    }

    public Position getTargetPiecePosition() {
        return targetPiecePosition;
    }

    public Position getDestination() {
        return destination;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(commandId, campType, targetPiecePosition, destination);
    }
}
