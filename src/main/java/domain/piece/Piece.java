package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;
import java.util.function.Function;

public abstract class Piece {
    private final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public abstract boolean canMove(Position source, Position target);

    public abstract List<Position> calculateRoute(Position source, Position target);

    public void validateMove(Position source, Position target, Function<Position, Piece> pieceAt) {
        if (!canMove(source, target)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        List<Piece> piecesOnRoute = calculateRoute(source, target).stream()
                .map(pieceAt)
                .toList();

        validateRoute(piecesOnRoute, pieceAt.apply(target));
    }

    public void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece) {
        if (piecesOnRoute.stream().anyMatch(Piece::isNotEmpty)) {
            throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
        }

        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public boolean isNotEmpty() {
        return true;
    }

    public boolean isAlly(Piece other) {
        return this.team.isAllyWith(other.team);
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

    public Team getTeam() {
        return team;
    }

    public abstract double score();

    protected int forwardDirection() {
        return team.forwardRowDirection();
    }
}
