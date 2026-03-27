package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.Position;
import domain.Team;
import java.util.List;

public abstract class Piece {

    protected final PieceProperty pieceProperty;
    protected final MoveStrategy moveStrategy;

    Piece(PieceProperty pieceProperty, MoveStrategy moveStrategy) {
        this.pieceProperty = pieceProperty;
        this.moveStrategy = moveStrategy;
    }

    public boolean isMoveAble(Position destination) {
        return moveStrategy.isMoveAble(destination);
    };

    public boolean isInvalidPath(Position destination, List<Position> piecePositions) {
        return moveStrategy.isInvalidPath(destination, piecePositions);
    }

    public String name () {
        return this.pieceProperty.pieceType();
    }

    public boolean isGreenTeam() {
        return this.pieceProperty.team().equals(Team.GREEN);
    }

    public boolean isRedTeam() {
        return this.pieceProperty.team().equals(Team.RED);
    }

    public boolean isNoneTeam() {
        return this.pieceProperty.team().equals(Team.NONE);
    }

    public Position position() {
        return moveStrategy.position();
    }
}
