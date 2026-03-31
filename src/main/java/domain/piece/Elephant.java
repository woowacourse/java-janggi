package domain.piece;

import static domain.piece.error.ErrorMessage.IMPOSSIBLE_MOVE;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.coordination.MoveDeltas;
import domain.piece.error.PieceException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Elephant extends Piece {

    private static final MoveDeltas MOVABLE_ABSOLUTE_LOCATION = MoveDeltas.of(Set.of(
            new MoveDelta(2, 3),
            new MoveDelta(3, 2)
    ));
    private static final int MAX_STEP = 3;
    private static final int HALF_STEP = 2;


    public Elephant(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();

        validateLocation(absDifferent);

        List<Coordination> intermediateCoordinations = createIntermediateCoordinations(
                from,
                different.deltaColumn(),
                different.deltaRow(),
                absDifferent.deltaColumn()
        );

        validatePathClear(board, intermediateCoordinations);
        validateSameTeam(from, to, board);
    }

    private void validateLocation(MoveDelta absDifferent) {
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(absDifferent)) {
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
        Coordination firstIntermediate = from.plus(columnDifferent / MAX_STEP, 0);
        Coordination secondIntermediate = from.plus(columnDifferent * HALF_STEP / MAX_STEP, rowDifferent / HALF_STEP);
        return List.of(firstIntermediate, secondIntermediate);
    }

    private List<Coordination> createVerticalIntermediateCoordinations(
            Coordination from,
            int columnDifferent,
            int rowDifferent
    ) {
        Coordination firstIntermediate = from.plus(0, rowDifferent / MAX_STEP);
        Coordination secondIntermediate = from.plus(columnDifferent / HALF_STEP, rowDifferent * HALF_STEP / MAX_STEP);
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
