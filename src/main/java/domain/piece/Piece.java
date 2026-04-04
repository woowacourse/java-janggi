package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public abstract class Piece {
    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public abstract boolean canMove(Position source, Position target);

    public abstract List<Position> calculateRoute(Position source, Position target);

    public void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece) {
        if (piecesOnRoute.stream().anyMatch(Piece::isNotEmpty)) {
            throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
        }

        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public boolean isNotEmpty() {
        return this.type != PieceType.EMPTY;
    }

    public boolean isAlly(Piece other) {
        return this.team != Team.NONE && this.team == other.team;
    }

    public boolean belongsTo(Team team) {
        return this.team == team;
    }

    public boolean canBeTargetedByCannon() {
        return true;
    }

    public boolean canBeJumpedByCannon() {
        return true;
    }

    public String display(PieceAppearance colorizer) {
        return colorizer.colorize(team, type);
    }

    protected int forwardDirection() {
        return team.forwardRowDirection();
    }

    @Override
    public String toString() {
        return type.name();
    }
}
