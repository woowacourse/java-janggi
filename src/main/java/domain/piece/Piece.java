package domain.piece;

import domain.game.Score;
import domain.game.Team;
import domain.position.Position;
import java.util.List;

public abstract class Piece {
    private final Team team;
    private final PieceDefinition type;

    protected Piece(Team team, PieceDefinition type) {
        this.team = team;
        this.type = type;
    }

    public abstract boolean canMove(Position source, Position target);

    public abstract List<Position> searchRoute(Position src, Position dest);

    public boolean isChoTeam() {
        return team.equals(Team.CHO);
    }

    public Score getScore() {
        return this.type.getScore();
    }

    public void validateSameTurnAndPiece(Team currentTeam) {
        if (this.team != currentTeam) {
            throw new IllegalArgumentException("현재 차례의 기물만 이동할 수 있습니다.");
        }
    }

    private boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isAlly(Piece other) {
        return isSameTeam(other);
    }

    protected int forwardDirection() {
        return team.forwardRowDirection();
    }

    @Override
    public String toString() {
        return type.name();
    }

    public void validateCanMove(Position src, Position dest) {
        if (!canMove(src, dest)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    public void validateDestination(Piece destPiece) {
        if (destPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public Team getTeam() {
        return team;
    }

    public PieceDefinition getType() {
        return type;
    }
}
