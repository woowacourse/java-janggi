package domain.piece;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Elephant extends Piece {

    private static final List<MoveDelta> MOVABLE_ABSOLUTE_LOCATION = List.of(
            new MoveDelta(2, 3),
            new MoveDelta(3, 2)
    );
    private static final int MAX_STEP = 3;
    private static final int HALF_STEP = 2;


    public Elephant(Team team) {
        super(team);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();

        validateLocation(absDifferent);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.ELEPHANT;
    }

    private void validateLocation(MoveDelta absDifferent) {
        if (!MOVABLE_ABSOLUTE_LOCATION.contains(absDifferent)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();
        return createIntermediateCoordinations(
                from,
                different.deltaColumn(),
                different.deltaRow(),
                absDifferent.deltaColumn()
        );
    }

    private List<Coordination> createIntermediateCoordinations(
            Coordination from,
            int columnDifferent,
            int rowDifferent,
            int absColumnDifferent
    ) {
        if (absColumnDifferent == MAX_STEP) {
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

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
