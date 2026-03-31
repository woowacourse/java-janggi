package domain.coordination;

import java.util.Set;

public class MoveDeltas {

    private static final String ERROR_EMPTY = "비어 있을 수 없습니다.";

    private final Set<MoveDelta> deltas;

    private MoveDeltas(Set<MoveDelta> deltas) {
        validateIsEmpty(deltas);
        this.deltas = Set.copyOf(deltas);
    }

    private static void validateIsEmpty(Set<MoveDelta> deltas) {
        if (deltas == null || deltas.isEmpty()) {
            throw new IllegalArgumentException(ERROR_EMPTY);
        }
    }

    public static MoveDeltas of(Set<MoveDelta> deltas) {
        return new MoveDeltas(deltas);
    }

    public boolean contains(MoveDelta delta) {
        return deltas.contains(delta);
    }
}
