package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import domain.piece.rule.PieceRule;
import java.util.List;

public abstract class Piece {

    protected static final String IMPOSSIBLE_MOVE = "기물이 움직일 수 없는 위치입니다.";

    protected final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public Team team() {
        return team;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    public void validateNotSameTeam(Piece piece) {
        if (!isEmptyPiece(piece) && piece.isSameTeam(this.team)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private boolean isEmptyPiece(Piece piece) {
        return piece.isEmpty();
    }

    public boolean isAliveGeneral() {
        return false;
    }

    public abstract PieceType pieceType();

    public int score() {
        return pieceType().score();
    }

    public void validateRule(Coordination from, Coordination to) {
        rule().validate(from, to, team);
    }

    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        return rule().resolvePath(from, to, team);
    }

    public void validatePath(List<Piece> piecesOnPath) {
        rule().validatePath(piecesOnPath);
    }

    protected abstract PieceRule rule();
}
