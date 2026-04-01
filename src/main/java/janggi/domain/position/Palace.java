package janggi.domain.position;

import java.util.List;
import java.util.Map;

import static janggi.domain.position.Direction.*;

/**
 * 궁성(초나라 기준):
 * (1,4), (1,5), (1,6)
 * (2,4), (2,5), (2,6),
 * (3,4), (3,5), (3,6)
 * 궁성은 단순히 영역이 아니라 각 위치에 대해 움직일 수 있는 방향이 존재함. (2,4) -> (1,5)로는 이동 불가
 * 나라에 따라도 달라짐
 */
public class Palace {

    // key: Position, value: 각 Position에서 궁성 내에서 이동할 수 있는 방향 목록
    private static Map<Position, List<Direction>> palacePositionDirectionMap = Map.of(
            Position.from(1, 4), List.of(SOUTH, SOUTHEAST, EAST),
            Position.from(1, 5), List.of(WEST, SOUTHEAST, EAST),
            Position.from(1, 6), List.of(SOUTHWEST, SOUTHEAST, SOUTH),
            Position.from(2, 4), List.of(SOUTH, EAST, NORTH),
            Position.from(2, 5), List.of(WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH, NORTHWEST),
            Position.from(2, 6), List.of(WEST, SOUTH, NORTH),
            Position.from(3, 4), List.of(EAST, NORTHEAST, NORTH),
            Position.from(3, 5), List.of(WEST, EAST, NORTH),
            Position.from(3, 6), List.of(WEST, NORTH, NORTHWEST)
    );

    public static boolean isPalace(Position position) {
        return palacePositionDirectionMap.containsKey(position) ||
                palacePositionDirectionMap.containsKey(position.flip());
    }

}
