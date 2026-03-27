package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.Position;
import java.util.List;

public class Piece {

    private final PieceProperty pieceProperty;
    private final MoveStrategy moveStrategy;

    public Piece(PieceProperty pieceProperty, MoveStrategy moveStrategy) {
        this.pieceProperty = pieceProperty;
        this.moveStrategy = moveStrategy;
    }

    public void moved(Position movedPosition) {
        this.moveStrategy.changePosition(movedPosition);
        moveStrategy.updateRoute();
    }

    public boolean isMoveAble(Position destination) {
        return moveStrategy.isMoveAble(destination);
    };

    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        return moveStrategy.hasPieceOnPath(destination, piecePositions);
    }

    public boolean isGeneral() {
        return pieceProperty.isGeneral();
    }

    public boolean isCannon() {
        return pieceProperty.isCannon();
    }

    public String name () {
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

    public Position position() {
        return moveStrategy.position();
    }
}
