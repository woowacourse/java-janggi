package domain.piece;

import domain.board.PathPieces;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.rule.PathGenerator;
import domain.strategy.MovementStrategy;
import java.util.Objects;

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
        return pathGenerator.calculatePath(src, dest);
    }

    public boolean validatePath(PathPieces pathPieces) {
        return movementStrategy.validatePath(pathPieces);
    }

    public String getPieceString() {
        return pieceType.getSymbol();
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return team == piece.team && 
               pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }
}
