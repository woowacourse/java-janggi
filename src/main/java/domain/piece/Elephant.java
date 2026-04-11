package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.InvalidMovementException;

import java.util.List;

public class Elephant extends Piece {

    private static final List<List<Integer>> MOVABLE_ABSOLUTE_LOCATION = List.of(List.of(2, 3), List.of(3, 2));

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.ELEPHANT;
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
        if (absCol == 3) {
            return createHorizontalPath(from, colDiff, rowDiff);
        }
        return createVerticalPath(from, colDiff, rowDiff);
    }

    private List<Coordination> createHorizontalPath(Coordination from, int colDiff, int rowDiff) {
        Coordination first = from.plus(colDiff / 3, 0);
        Coordination second = from.plus(colDiff * 2 / 3, rowDiff / 2);
        return List.of(first, second);
    }

    private List<Coordination> createVerticalPath(Coordination from, int colDiff, int rowDiff) {
        Coordination first = from.plus(0, rowDiff / 3);
        Coordination second = from.plus(colDiff / 2, rowDiff * 2 / 3);
        return List.of(first, second);
    }
}
