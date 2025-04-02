package janggi.game;

import janggi.rule.CampType;
import janggi.value.Position;

public final class MovePieceCommand {

    private final CampType campType;
    private final Position targetPiecePosition;
    private final Position destination;

    public MovePieceCommand(CampType campType, Position targetPiecePosition, Position destination) {
        this.campType = campType;
        this.targetPiecePosition = targetPiecePosition;
        this.destination = destination;
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
}
