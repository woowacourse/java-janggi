package domain.movement;

import domain.path.Direction;
import domain.vo.Position;
import java.util.List;

public interface Movement {

    List<Position> buildRoute(List<Direction> route, Position sourcePosition, Position targetPosition);
}
