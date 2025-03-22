package janggi.piece;

import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;
import java.util.Map;

public final class Cannon extends Piece {

    public Cannon(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Cannon of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Cannon(team, movingRules);
    }

    @Override
    public void validateMove(final Position start, final Position end, final Map<Position, Piece> board) {
        Position route = start;
        int count = 0;
        for (MoveVector vector : movingRules.findMatchRule(start, end).getVectorsWithoutLast()) {
            route = route.add(vector);
            if (board.containsKey(route) && board.get(route).type() == Type.CANNON) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어 넘을 수 없습니다.");
            }
            if (board.containsKey(route) && ++count == 2) {
                throw new IllegalArgumentException("[ERROR] 포는 단 하나의 기물만 뛰어 넘을 수 있습니다.");
            }
        }
        if (count == 0) {
            throw new IllegalArgumentException("[ERROR] 포는 적어도 하나의 기물을 뛰어 넘어야 합니다.");
        }
    }

    @Override
    public Type type() {
        return Type.CANNON;
    }
}
