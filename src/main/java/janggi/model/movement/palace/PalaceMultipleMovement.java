package janggi.model.movement.palace;

import janggi.model.palace.Palaces;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.absolute.Row;
import java.util.List;

public class PalaceMultipleMovement extends PalaceMovement {

    private static final int ONE_STEP = 1;


    public PalaceMultipleMovement(Palaces palaces) {
        super(palaces);
    }

    @Override
    public PositionPath move(Position from, Position to) {
        if (!supports(from, to)) {
            throw new IllegalArgumentException("from과 to는 같은 궁성 안에 있어야 합니다.");
        }

        int distance = from.getDistanceTo(to);

        if (distance == ONE_STEP) {
            return moveOneStep(from, to);
        }

        return moveTwoStep(from, to);
    }

    private PositionPath moveTwoStep(Position from, Position to) {
        Position middle = new Position(
                Row.of((from.row().getValue() + to.row().getValue()) / 2),
                Column.of((from.column().getValue() + to.column().getValue()) / 2)
        );

        if (!palaces.isAdjacentInSamePalace(from, middle) || !palaces.isAdjacentInSamePalace(middle, to)) {
            throw new IllegalArgumentException("해당 경로로는 이동할 수 없습니다.");
        }

        return new PositionPath(List.of(middle));
    }
}
