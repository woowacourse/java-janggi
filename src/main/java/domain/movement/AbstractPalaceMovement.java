package domain.movement;

import domain.coordination.Coordination;
import domain.piece.error.PalaceMovementException;

import java.util.List;
import java.util.Map;

public abstract class AbstractPalaceMovement implements PalaceMovement {

    private static final String IMPOSSIBLE_PALACE_MOVE_MESSAGE = "궁성 내 이동 규칙에 어긋납니다.";

    private final Map<Coordination, List<Coordination>> palace;

    public AbstractPalaceMovement(Map<Coordination, List<Coordination>> palace) {
        this.palace = palace;
    }

    @Override
    public boolean isPalace(Coordination from, Coordination to) {
        return palace.containsKey(from) && palace.containsKey(to);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (!palace.getOrDefault(from, List.of()).contains(to)) {
            throw new PalaceMovementException(IMPOSSIBLE_PALACE_MOVE_MESSAGE);
        }
    }
}
