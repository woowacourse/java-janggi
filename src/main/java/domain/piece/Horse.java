package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.InvalidMovementException;

import java.util.List;

public class Horse extends Piece {

    private static final List<List<Integer>> MOVABLE_ABSOLUTE_LOCATION = List.of(List.of(1, 2), List.of(2, 1));

    public Horse(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        int absCol = Math.abs(from.differentColumn(to));
        int absRow = Math.abs(from.differentRow(to));
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(List.of(absCol, absRow))) {
            throw new InvalidMovementException(IMPOSSIBLE_MOVE_MESSAGE);
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        int colDiff = from.differentColumn(to);
        int rowDiff = from.differentRow(to);
        int absCol = Math.abs(colDiff);
        if (absCol == 2) {
            return List.of(from.plus(colDiff / 2, 0));
        }
        return List.of(from.plus(0, rowDiff / 2));
    }
}
