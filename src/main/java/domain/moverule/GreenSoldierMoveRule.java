package domain.moverule;

import domain.MoveRules;
import domain.Position;
import java.util.List;
import java.util.function.Function;

public enum GreenSoldierMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<Function<Position, Position>> moveSteps;

    GreenSoldierMoveRule(List<Function<Position, Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    @Override
    public List<Function<Position, Position>> moveSteps() {
        return this.moveSteps;
    }
}
