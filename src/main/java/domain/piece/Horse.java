package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;
import java.util.Map;

import static util.ErrorMessage.IMPOSSIBLE_MOVE;

public class Horse extends Piece {

    private static final List<List<Integer>> MOVABLE_ABSOLUTE_LOCATION = List.of(List.of(1, 2), List.of(2, 1));

    public Horse(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        int colDifferent = from.differentColumn(to);
        int rowDifferent = from.differentRow(to);
        int absColumnDifferent = Math.abs(colDifferent);
        int absRowDifferent = Math.abs(rowDifferent);

        validateLocation(absColumnDifferent, absRowDifferent);

        Coordination intermediateColumn = from.plus(colDifferent / 2, 0);
        Coordination intermediateRow = from.plus(0, rowDifferent / 2);

        validateDirection(board, absColumnDifferent, intermediateColumn, absRowDifferent, intermediateRow);

        validateSameTeam(from, to, board);
    }

    private void validateLocation(int absColumnDifferent, int absRowDifferent) {
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(List.of(absColumnDifferent, absRowDifferent))) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private void validateDirection(Map<Coordination, Piece> board, int absColumnDifferent, Coordination intermediateColumn, int absRowDifferent, Coordination intermediateRow) {
        validateDirection(absColumnDifferent, board, intermediateColumn);
        validateDirection(absRowDifferent, board, intermediateRow);
    }

    private void validateDirection(int absDifferent, Map<Coordination, Piece> board, Coordination intermediateCoordination) {
        if (absDifferent == 2) {
            validatePathClear(board, intermediateCoordination);
        }
    }

    private void validatePathClear(Map<Coordination, Piece> board, Coordination intermediateCoordination) {
        if (!board.get(intermediateCoordination).isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
