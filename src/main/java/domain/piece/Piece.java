package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    protected abstract MovementStrategy getMovementStrategy();

    protected abstract PathGenerator getPathGenerator();

    public Optional<Path> calculatePath(Position source, Position destination) {
        return this.getPathGenerator().calculatePath(source, destination);
    }

    public Set<Position> findCandidateDestinations(Position source) {
        return this.getPathGenerator().findCandidateDestinations(source);
    }

    public boolean isValidPath(Path path, PathPieces pathPieces) {
        return this.getMovementStrategy().isValidPath(path, pathPieces);
    }

    public String getPieceString() {
        return pieceType.getSymbol();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isDifferentTeam(Piece piece) {
        return this.team != piece.team;
    }

    public boolean isNone() {
        return false;
    }

    public boolean isPo() {
        return false;
    }

    public boolean isJang() {
        return false;
    }

    public boolean isCho() {
        return team == Team.CHO;
    }

    public boolean isHan() {
        return team == Team.HAN;
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
        return team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }

    public Team getTeam() {
        return team;
    }

    public double getPoint() {
        return pieceType.getPoint();
    }
}
