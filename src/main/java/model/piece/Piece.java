package model.piece;

import java.util.List;
import java.util.Objects;
import model.position.Position;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public String getName() {
        return pieceType.getName();
    }

    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return pieceType.calculateAllDirection(departure, arrival);
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }

    public int getScore() {
        return pieceType.getScore();
    }

    public boolean isSameTeam(Piece piece) {
        return this.team == piece.getTeam();
    }

    public Team getTeam() {
        return team;
    }

    public String getType() {
        return pieceType.name();
    }

    @Override
    public boolean equals(Object o) {
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
}
