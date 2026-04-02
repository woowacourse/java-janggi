package domain.moverule;

import domain.MoveRules;
import domain.Position;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

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

    private final List<UnaryOperator<Position>> moveSteps;

    GeneralMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }
}
