package domain;

import domain.strategy.MoveStrategy;
import java.util.List;

public class Piece {

    private final PieceProperty pieceProperty;
    private final MoveStrategy moveStrategy;

    public Piece(PieceProperty pieceProperty, Position position) {
        this.pieceProperty = pieceProperty;
        this.moveStrategy = pieceProperty.moveStrategy(position);
    }

    public Piece moved(Position position) {
        return new Piece(this.pieceProperty, position);
    }

    public static Piece None(Position position) {
        return new Piece(PieceProperty.none(), position);
    }

    public boolean isMoveable(Position destination) {
        return moveStrategy.isMoveAble(destination);
    }

    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        return moveStrategy.isPathRestricted(destination, piecePositions);
    }

    public boolean isGeneral() {
        return pieceProperty.isGeneral();
    }

    public boolean isCannon() {
        return pieceProperty.isCannon();
    }

    public String name() {
        return pieceProperty.name();
    }

    public boolean isGreenTeam() {
        return pieceProperty.isGreenTeam();
    }

    public boolean isRedTeam() {
        return pieceProperty.isRedTeam();
    }

    public boolean isNone() {
        return pieceProperty.isNone();
    }

    public Position position() {
        return moveStrategy.position();
    }

    public int point() {
        return pieceProperty.point();
    }
}
