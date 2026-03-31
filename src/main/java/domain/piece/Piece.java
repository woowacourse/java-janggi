package domain.piece;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.error.PieceException;
import util.ErrorMessage;

import java.util.List;

public abstract class Piece {

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

    public void validateSameTeam(Turn turn) {
        turn.validateSameTeam(team);
    }

    public void validateNotSameTeam(Piece target) {
        if (!target.isEmpty() && this.team == target.team) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }

    public void validatePath(List<Piece> piecesOnPath) {
        boolean hasBlocker = piecesOnPath.stream().anyMatch(p -> !p.isEmpty());
        if (hasBlocker) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }

    public abstract PieceType pieceType();

    public abstract void validateRule(Coordination from, Coordination to);

    public abstract List<Coordination> resolvePath(Coordination from, Coordination to);

    public boolean isGeneral() {
        return false;
    }
}
