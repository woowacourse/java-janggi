package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;
import java.util.Objects;

public class Piece {

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

    public Path calculatePath(Position source, Position destination) {
        return pathGenerator.calculatePath(source, destination);
    }

    public boolean validatePath(PathPieces pathPieces) {
        return movementStrategy.validatePath(pathPieces);
    }

    public String getPieceString() {
        return pieceType.getSymbol();
    }

    public String getTeamString() {
        if (team == Team.CHO) {
            return "C";
        }
        if (team == Team.HAN) {
            return "H";
        }
        return "";
    }

    public boolean isDifferentTeam(Team team) {
        return this.team != team;
    }

    public boolean isDifferentTeam(Piece piece) {
        return this.team != piece.team;
    }

    public boolean isNotNone() {
        return this instanceof None;
    }

    public boolean isPo() {
        return this instanceof Po;
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
