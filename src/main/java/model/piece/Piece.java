package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Position;

public abstract class Piece {
    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    private static void validatePosition(Position current, Position next) {
        if (current.equals(next)) {
            throw new IllegalArgumentException("기물은 제자리로 이동할 수 없습니다.");
        }
    }

    public List<Position> pathTo(Position current, Position next) {
        validatePosition(current, next);
        validateMove(current, next);
        return extractPath(current, next);
    }

    public boolean isSameTeam(Team team) {
        return team() == team;
    }

    public void validatePathCondition(List<Piece> pieces) {
        if (!pieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 있어 이동할 수 없는 위치입니다.");
        }
    }

    public void validateTarget(Piece otherPiece) {
        if (team() == otherPiece.team()) {
            throw new IllegalArgumentException("아군이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public boolean isSameType(PieceType type) {
        return type() == type;
    }

    public double score() {
        return type().getScore();
    }

    protected abstract void validateMove(Position current, Position next);

    protected boolean isSameType(Piece piece) {
        return isSameType(piece.type());
    }

    protected List<Position> extractPath(Position current, Position next) {
        return List.of();
    }

    public Team team() {
        return team;
    }

    public PieceType type() {
        return type;
    }
}
