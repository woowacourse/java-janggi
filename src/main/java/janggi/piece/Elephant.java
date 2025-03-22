package janggi.piece;

import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;
import java.util.Map;

public final class Elephant extends Piece {

    private Elephant(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Elephant of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.elephant();
        return new Elephant(team, movingRules);
    }

    @Override
    public void validateMove(final Position start, final Position end, final Map<Position, Piece> board) {
        final MovingRule matchRule = movingRules.findMatchRule(start, end);
        Position route = start;
        for (MoveVector vector : matchRule.getVectorsWithoutLast()) {
            route = route.add(vector);
            if (board.containsKey(route)) {
                throw new IllegalArgumentException("[ERROR] 기물의 이동 경로에 다른 기물이 있습니다.");
            }
        }
    }

    @Override
    public Type type() {
        return Type.ELEPHANT;
    }
}
