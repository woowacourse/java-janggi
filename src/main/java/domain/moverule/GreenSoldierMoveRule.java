package domain.moverule;

import domain.MoveRules;
import domain.Position;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public enum GreenSoldierMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    GreenSoldierMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }
}
