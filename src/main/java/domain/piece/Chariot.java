package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;
import java.util.Map;

import static util.ErrorMessage.IMPOSSIBLE_MOVE;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        validateLocation(from, to);
        validatePathClear(from, to, board);
        validateSameTeam(from, to, board);
    }

    private void validateLocation(Coordination from, Coordination to) {
        boolean movable = from.isSameRowDifferentColumn(to) || from.isSameColumnDifferentRow(to);
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private void validatePathClear(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        List<Coordination> path = resolvePath(from, to);
        for (Coordination coordination : path) {
            if (!board.get(coordination).isEmpty()) {
                throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
            }
        }
    }

    private List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (from.isSameColumnDifferentRow(to)) {
            return from.betweenRowCoordination(to);
        }
        return from.betweenColumnCoordination(to);
    }
}
