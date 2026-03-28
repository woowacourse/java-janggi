package domain.moverule;

import domain.MoveRules;
import domain.Position;
import java.util.List;
import java.util.function.Function;

public enum GeneralMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    UP_CROSS_RIGHT(List.of(Position::upCrossRight)),
    UP_CROSS_LEFT(List.of(Position::upCrossLeft)),

    DOWN(List.of(Position::down)),
    DOWN_CROSS_RIGHT(List.of(Position::downCrossRight)),
    DOWN_CROSS_LEFT(List.of(Position::downCrossLeft)),

    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<Function<Position, Position>> moveSteps;

    GeneralMoveRule(List<Function<Position, Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    @Override
    public List<Function<Position, Position>> moveSteps() {
        return this.moveSteps;
    }
}
