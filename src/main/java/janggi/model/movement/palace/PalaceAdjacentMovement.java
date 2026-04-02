package janggi.model.movement.palace;

import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public class PalaceAdjacentMovement extends PalaceMovement{

    @Override
    public PositionPath move(Position from, Position to) {
        if (!isAllInPalace(from, to)) {
            throw new IllegalArgumentException("궁성 밖의 위치가 포함돼 있습니다.");
        }

        if (!isAdjacent(from, to)) {
            throw new IllegalArgumentException("해당 경로로 이동할 수 없습니다.");
        }

        return new PositionPath(List.of());
    }
}
