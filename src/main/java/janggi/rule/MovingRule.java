package janggi.rule;

import java.util.ArrayList;
import java.util.List;

public final class MovingRule {

    private final List<Vector> movingRule;

    public MovingRule(final List<Vector> movingRule) {
        this.movingRule = movingRule;
    }

    public Vector sumAllVectors() {
        Vector sumUnit = new Vector(0, 0);
        for (Vector moveVector : movingRule) {
            sumUnit = sumUnit.add(moveVector);
        }
        return sumUnit;
    }

    public List<Vector> getVectorsWithoutLast() {
        final ArrayList<Vector> vectors = new ArrayList<>(movingRule);
        vectors.removeLast();
        return vectors;
    }
}
