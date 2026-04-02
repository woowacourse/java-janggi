package domain.moverule;

import domain.MoveRules;
import domain.Position;
import java.util.List;
import java.util.function.UnaryOperator;

public enum GuardMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    DOWN(List.of(Position::down)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    GuardMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }
}
