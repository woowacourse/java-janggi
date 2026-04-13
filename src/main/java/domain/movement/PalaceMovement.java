package domain.movement;

import domain.coordination.Coordination;

public interface PalaceMovement {

    boolean isPalace(Coordination from, Coordination to);

    void validateRule(Coordination from, Coordination to);
}
