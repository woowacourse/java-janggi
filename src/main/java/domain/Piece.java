package domain;

import domain.strategy.MoveStrategy;
import java.util.List;

public class Piece {

    private final PieceProperty pieceProperty;
    private final MoveStrategy moveStrategy;

    public Piece(PieceProperty pieceProperty, MoveStrategy moveStrategy) {
        this.pieceProperty = pieceProperty;
        this.moveStrategy = moveStrategy;
    }

    public void moveTo(Position destination) {
        this.moveStrategy.moveTo(destination);
        moveStrategy.updateRoute();
    }

    public boolean canMoveTo(Position destination) {
        return moveStrategy.canMoveTo(destination);
    }

    public boolean hasValidPathTo(Position destination, List<Position> occupiedPositions) {
        return moveStrategy.hasValidPathTo(destination, occupiedPositions);
    }

    public boolean isGeneral() {
        return pieceProperty.isGeneral();
    }

    public boolean isCannon() {
        return pieceProperty.isCannon();
    }

    public String pieceName() {
        return pieceProperty.name();
    }

    public boolean isGreenTeam() {
        return pieceProperty.isGreenTeam();
    }

    public boolean isRedTeam() {
        return pieceProperty.isRedTeam();
    }

    public boolean isNoneTeam() {
        return pieceProperty.isNoneTeam();
    }

    public Position currentPosition() {
        return moveStrategy.position();
    }
}
