package domain.move;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.Team;
import java.util.List;

public class HorseMovement implements Movement {

    private static final List<MoveDelta> MOVABLE_ABSOLUTE_LOCATION = List.of(
            new MoveDelta(1, 2),
            new MoveDelta(2, 1)
    );
    private static final int MAX_STEP = 2;

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        return MOVABLE_ABSOLUTE_LOCATION.contains(MoveDelta.between(from, to).absolute());
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        MoveDelta different = MoveDelta.between(from, to);
        MoveDelta absDifferent = different.absolute();

        Coordination intermediateColumn = from.plus(different.deltaColumn() / MAX_STEP, 0);
        Coordination intermediateRow = from.plus(0, different.deltaRow() / MAX_STEP);

        if (absDifferent.deltaColumn() == MAX_STEP) {
            return List.of(intermediateColumn);
        }
        if (absDifferent.deltaRow() == MAX_STEP) {
            return List.of(intermediateRow);
        }
        return List.of();
    }
}
