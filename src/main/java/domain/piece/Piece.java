package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.rule.PathGenerator;
import domain.strategy.MovementStrategy;

public abstract class Piece {

    private final Team team;
    private final PieceType pieceType;
    private final MovementStrategy movementStrategy;
    private final PathGenerator pathGenerator;

    public Piece(Team team, PieceType pieceType, MovementStrategy movementStrategy, PathGenerator pathGenerator) {
        this.team = team;
        this.pieceType = pieceType;
        this.movementStrategy = movementStrategy;
        this.pathGenerator = pathGenerator;
    }

    //경로 계산.
    public Path calculatePath(Position src, Position dest) {
        return pathGenerator.calculatePath(src,dest);
    };

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
