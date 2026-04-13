package domain.move;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.Team;
import java.util.List;

public class ElephantMovement implements Movement {

    private static final List<MoveDelta> MOVABLE_ABSOLUTE_LOCATION = List.of(
            new MoveDelta(2, 3),
            new MoveDelta(3, 2)
    );
    private static final int MAX_STEP = 3;
    private static final int HALF_STEP = 2;

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        return MOVABLE_ABSOLUTE_LOCATION.contains(MoveDelta.between(from, to).absolute());
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();
        if (absDifferent.deltaColumn() == MAX_STEP) {
            return createHorizontalIntermediateCoordinations(
                    from,
                    different.deltaColumn(),
                    different.deltaRow()
            );
        }
        return createVerticalIntermediateCoordinations(
                from,
                different.deltaColumn(),
                different.deltaRow()
        );
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
}
