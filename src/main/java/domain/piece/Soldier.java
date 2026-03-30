package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import util.ErrorMessage;

import java.util.List;
import java.util.Map;

public class Soldier extends Piece {

    private static final List<List<Integer>> CHO_MOVABLE_LOCATION = List.of(List.of(-1, 0), List.of(0, -1), List.of(1, 0));
    private static final List<List<Integer>> HAN_MOVABLE_LOCATION = List.of(List.of(-1, 0), List.of(0, 1), List.of(1, 0));

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        validateLocation(from, to);
        validateSameTeam(from, to, board);
    }

    private void validateLocation(Coordination from, Coordination to) {
        List<Integer> different = List.of(from.differentColumn(to), from.differentRow(to));

        if (team == Team.CHO) {
            validateLocation(CHO_MOVABLE_LOCATION, different);
            return;
        }
        validateLocation(HAN_MOVABLE_LOCATION, different);
    }

    private void validateLocation(List<List<Integer>> movableLocation, List<Integer> different) {
        boolean isMovable = movableLocation.contains(different);

        if (!isMovable) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
