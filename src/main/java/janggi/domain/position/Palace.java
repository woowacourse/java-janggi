package janggi.domain.position;

import janggi.domain.exception.DomainException;

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

    public static final String INVALID_PALACE_POSITION = "해당 위치(%d,%d)는 궁성이 아닙니다.";

    // key: Position, value: 각 Position에서 궁성 내에서 이동할 수 있는 방향 목록
    private final static Map<Position, List<Direction>> palacePositionDirectionMap = Map.ofEntries(
            Map.entry(Position.from(1, 4), List.of(SOUTH, SOUTHEAST, EAST)),
            Map.entry(Position.from(1, 5), List.of(WEST, SOUTH, EAST)),
            Map.entry(Position.from(1, 6), List.of(WEST, SOUTHWEST, SOUTH)),
            Map.entry(Position.from(2, 4), List.of(SOUTH, EAST, NORTH)),
            Map.entry(Position.from(2, 5), List.of(WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH, NORTHWEST)),
            Map.entry(Position.from(2, 6), List.of(WEST, SOUTH, NORTH)),
            Map.entry(Position.from(3, 4), List.of(EAST, NORTHEAST, NORTH)),
            Map.entry(Position.from(3, 5), List.of(WEST, EAST, NORTH)),
            Map.entry(Position.from(3, 6), List.of(WEST, NORTH, NORTHWEST)),

            Map.entry(Position.from(8, 4), List.of(SOUTH, SOUTHEAST, EAST)),
            Map.entry(Position.from(8, 5), List.of(WEST, EAST, NORTH)),
            Map.entry(Position.from(8, 6), List.of(WEST, SOUTHWEST, SOUTH)),
            Map.entry(Position.from(9, 4), List.of(SOUTH, EAST, NORTH)),
            Map.entry(Position.from(9, 5), List.of(WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH, NORTHWEST)),
            Map.entry(Position.from(9, 6), List.of(WEST, SOUTH, NORTH)),
            Map.entry(Position.from(10, 4), List.of(EAST, NORTHEAST, NORTH)),
            Map.entry(Position.from(10, 6), List.of(WEST, NORTH, NORTHWEST)),
            Map.entry(Position.from(10, 5), List.of(WEST, NORTH, EAST))
    );


    public static boolean isPalace(Position position) {
        return palacePositionDirectionMap.containsKey(position);
    }

    public static List<Direction> getMovableDirectionsAtPalace(Position position) {
        if(!isPalace(position)) {
            throw new DomainException(String.format(INVALID_PALACE_POSITION, position.row().row(), position.column().column()));
        }
        return palacePositionDirectionMap.get(position);
    }

}
