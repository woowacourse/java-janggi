package domain.movement;

import domain.coordination.Coordination;

public interface PalaceMovement {

    String IMPOSSIBLE_PALACE_MOVE_MESSAGE = "궁성 내 이동 규칙에 어긋납니다.";

    boolean isPalace(Coordination from, Coordination to);

    void validateRule(Coordination from, Coordination to);
}
