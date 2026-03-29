package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;
import java.util.Objects;

public abstract class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    protected abstract MovementStrategy getMovementStrategy();

    protected abstract PathGenerator getPathGenerator();

    public Path calculatePath(Position source, Position destination) {
        return this.getPathGenerator().calculatePath(source, destination);
    }

    public boolean validatePath(PathPieces pathPieces) {
        return this.getMovementStrategy().validatePath(pathPieces);
    }

    public boolean isDifferentTeam(Team team) {
        if (team == null) {
            return false;
        }
        return this.team != team;
    }

    public boolean isDifferentTeam(Piece piece) {
        if (!piece.isNotNone()) {
            return true;
        }
        return this.team != piece.team;
    }

    public boolean isNotNone() {
        return this.pieceType != PieceType.NONE;
    }

    public boolean isPo() {
        return this.pieceType == PieceType.PO;
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

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}