package domain.piece.policy;

import domain.PathContext;

public interface MovementPolicy {
    void check(PathContext pathContext);
}
