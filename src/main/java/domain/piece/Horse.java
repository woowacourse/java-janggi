package domain.piece;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final List<MoveDelta> MOVABLE_ABSOLUTE_LOCATION = List.of(
            new MoveDelta(1, 2),
            new MoveDelta(2, 1)
    );
    private static final int MAX_STEP = 2;


    public Horse(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();

        validateLocation(absDifferent);

        int colDifferent = different.deltaColumn();
        int rowDifferent = different.deltaRow();
        int absColumnDifferent = absDifferent.deltaColumn();
        int absRowDifferent = absDifferent.deltaRow();

        Coordination intermediateColumn = from.plus(colDifferent / MAX_STEP, 0);
        Coordination intermediateRow = from.plus(0, rowDifferent / MAX_STEP);

        validateDirection(board, absColumnDifferent, intermediateColumn, absRowDifferent, intermediateRow);
        validateSameTeam(from, to, board);
    }

    private void validateLocation(MoveDelta absDifferent) {
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(absDifferent)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private void validateDirection(Map<Coordination, Piece> board, int absColumnDifferent,
                                   Coordination intermediateColumn, int absRowDifferent, Coordination intermediateRow) {
        validateDirection(absColumnDifferent, board, intermediateColumn);
        validateDirection(absRowDifferent, board, intermediateRow);
    }

    private void validateDirection(int absDifferent, Map<Coordination, Piece> board,
                                   Coordination intermediateCoordination) {
        if (absDifferent == 2) {
            validatePathClear(board, intermediateCoordination);
        }
    }

    private void validatePathClear(Map<Coordination, Piece> board, Coordination intermediateCoordination) {
        if (!board.get(intermediateCoordination).isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
