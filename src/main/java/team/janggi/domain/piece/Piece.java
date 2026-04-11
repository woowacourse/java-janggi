package team.janggi.domain.piece;

import java.util.Map;
import java.util.Objects;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.movestrategy.MoveStrategy;

public class Piece {
    private Team team;
    private PieceType pieceType;
    private MoveStrategy moveStrategy;

    public Piece(Team team, PieceType pieceType, MoveStrategy moveStrategy) {
        this.team = team;
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
    }

    public boolean canMove(Position from,
                           Position to,
                           Map<Position, Piece> mapStatus) {

        return moveStrategy.calculateMove(from, to, mapStatus);
    }

    public boolean isSameTeam(Piece otherPiece) {
        return isSameTeam(otherPiece.team);
    }

    public boolean isSameTeam(Team otherTeam) {
        return this.team == otherTeam;
    }

    public boolean isSamePieceType(PieceType otherPieceType) {
        return this.pieceType == otherPieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
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
        return team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, getClass());
    }
}
