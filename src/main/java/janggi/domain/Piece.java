package janggi.domain;

import janggi.domain.moveRules.MoveRule;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Piece {

    private final Team team;
    private final PieceType pieceType;
    private final MoveRule moveRule;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
        this.moveRule = pieceType.getMoveRule();
    }

    public List<Position> findAvailableDestinations(Position position, Map<Position, Piece> state) {
        return moveRule.calculateAvailablePositions(position, team, state);
    }

    public boolean isEnemy(Team targetTeam) {
        return !this.team.isSameTeam(targetTeam);
    }

    public boolean isPo() {
        return pieceType == PieceType.PO;
    }

    public boolean isKing() {
        return pieceType == PieceType.KING;
    }

    public boolean isSameTeam(Team targetTeam) {
        return this.team.isSameTeam(targetTeam);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }

    public boolean sameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }
}
