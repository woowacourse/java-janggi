package domain.moverule;

import domain.MoveRules;
import domain.Position;
import java.util.List;
import java.util.function.Function;

public enum GuardMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    DOWN(List.of(Position::down)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<Function<Position, Position>> moveSteps;

    GuardMoveRule(List<Function<Position, Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    @Override
    public List<Function<Position, Position>> moveSteps() {
        return this.moveSteps;
    }
}
