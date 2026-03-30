package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;
import java.util.Map;

import static util.ErrorMessage.IMPOSSIBLE_MOVE;

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
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        int columnDifferent = from.differentColumn(to);
        int rowDifferent = from.differentRow(to);
        int absColumnDifferent = Math.abs(columnDifferent);
        int absRowDifferent = Math.abs(rowDifferent);

        validateLocation(absColumnDifferent, absRowDifferent);

        List<Coordination> intermediateCoordinations = createIntermediateCoordinations(
                from,
                columnDifferent,
                rowDifferent,
                absColumnDifferent
        );

        validatePathClear(board, intermediateCoordinations);
        validateSameTeam(from, to, board);
    }

    private void validateLocation(int absColumnDifferent, int absRowDifferent) {
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(List.of(absColumnDifferent, absRowDifferent))) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private List<Coordination> createIntermediateCoordinations(
            Coordination from,
            int columnDifferent,
            int rowDifferent,
            int absColumnDifferent
    ) {
        if (absColumnDifferent == 3) {
            return createHorizontalIntermediateCoordinations(from, columnDifferent, rowDifferent);
        }
        return createVerticalIntermediateCoordinations(from, columnDifferent, rowDifferent);
    }

    private List<Coordination> createHorizontalIntermediateCoordinations(
            Coordination from,
            int columnDifferent,
            int rowDifferent
    ) {
        Coordination firstIntermediate = from.plus(columnDifferent / 3, 0);
        Coordination secondIntermediate = from.plus(columnDifferent * 2 / 3, rowDifferent / 2);
        return List.of(firstIntermediate, secondIntermediate);
    }

    private List<Coordination> createVerticalIntermediateCoordinations(
            Coordination from,
            int columnDifferent,
            int rowDifferent
    ) {
        Coordination firstIntermediate = from.plus(0, rowDifferent / 3);
        Coordination secondIntermediate = from.plus(columnDifferent / 2, rowDifferent * 2 / 3);
        return List.of(firstIntermediate, secondIntermediate);
    }

    private void validatePathClear(Map<Coordination, Piece> board, List<Coordination> intermediateCoordinations) {
        for (Coordination intermediateCoordination : intermediateCoordinations) {
            validateEmpty(board, intermediateCoordination);
        }
    }

    private void validateEmpty(Map<Coordination, Piece> board, Coordination intermediateCoordination) {
        if (!board.get(intermediateCoordination).isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
