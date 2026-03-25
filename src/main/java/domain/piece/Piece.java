package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;

public abstract class Piece {

    private final Team team;
    private final PieceType pieceType;
    private final MovementStrategy movementStrategy;

    public Piece(Team team, PieceType pieceType, MovementStrategy movementStrategy) {
        this.team = team;
        this.pieceType = pieceType;
        this.movementStrategy = movementStrategy;
    }

    //경로 계산.
    public abstract Path calculatePath(Position src, Position dest);

    public String getPieceString() {
        return pieceType.getSymbol();
    }

    public Team getTeam() {
        return team;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }
}
