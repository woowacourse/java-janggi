package domain;

import domain.strategy.MoveStrategy;
import java.util.List;

public class Piece {

    private final PieceProperty pieceProperty;
    private final MoveStrategy moveStrategy;

    private Piece(PieceProperty pieceProperty, MoveStrategy moveStrategy) {
        this.pieceProperty = pieceProperty;
        this.moveStrategy = moveStrategy;
    }

    public static Piece of(PieceProperty pieceProperty, MoveStrategy moveStrategy) {
        return new Piece(pieceProperty, moveStrategy);
    }

    public boolean canMoveTo(Position currentPosition, Position destination) {
        return moveStrategy.canMoveTo(currentPosition, destination);
    }

    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return moveStrategy.hasValidPathTo(currentPosition, destination, occupiedPositions);
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

    public int score() {
        return pieceProperty.score();
    }
}
