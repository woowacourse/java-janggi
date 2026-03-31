package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import util.ErrorMessage;

import java.util.List;
import java.util.Map;

public class General extends Piece {

    private static final List<List<Integer>> MOVABLE_LOCATION = List.of(List.of(-1, 0), List.of(0, -1), List.of(1, 0), List.of(0, 1));

    public General(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GENERAL;
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        validateLocation(from, to);
        validateNotSameTeam(from, to, board);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    private void validateLocation(Coordination from, Coordination to) {
        List<Integer> different = List.of(from.differentColumn(to), from.differentRow(to));

        validateLocation(different);
    }

    private void validateLocation(List<Integer> different) {
        boolean isMovable = MOVABLE_LOCATION.contains(different);

        if (!isMovable) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
