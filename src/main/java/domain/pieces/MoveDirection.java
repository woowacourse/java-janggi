package domain.pieces;

import domain.Camp;
import domain.Direction;

import java.util.List;

public class MoveDirection {
    public static List<Direction> ofLinear() {
        return List.of(
                Direction.NORTH,
                Direction.SOUTH,
                Direction.EAST,
                Direction.WEST
        );
    }

    public static List<List<Direction>> ofHorse() {
        return List.of(
                List.of(Direction.NORTH, Direction.NORTHEAST),
                List.of(Direction.NORTH, Direction.NORTHWEST),
                List.of(Direction.SOUTH, Direction.SOUTHEAST),
                List.of(Direction.SOUTH, Direction.SOUTHWEST),
                List.of(Direction.EAST, Direction.NORTHEAST),
                List.of(Direction.EAST, Direction.SOUTHEAST),
                List.of(Direction.WEST, Direction.NORTHWEST),
                List.of(Direction.WEST, Direction.SOUTHWEST)
        );
    }

    public static List<List<Direction>> ofElephant() {
        return List.of(
                List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST),
                List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST),
                List.of(Direction.SOUTH, Direction.SOUTHEAST, Direction.SOUTHEAST),
                List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST),
                List.of(Direction.EAST, Direction.NORTHEAST, Direction.NORTHEAST),
                List.of(Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST),
                List.of(Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST),
                List.of(Direction.WEST, Direction.SOUTHWEST, Direction.SOUTHWEST)
        );
    }

    public static List<List<Direction>> ofAllAround() {
        return List.of(
                List.of(Direction.NORTH),
                List.of(Direction.SOUTH),
                List.of(Direction.WEST),
                List.of(Direction.EAST),
                List.of(Direction.NORTHWEST),
                List.of(Direction.NORTHEAST),
                List.of(Direction.SOUTHWEST),
                List.of(Direction.SOUTHEAST)
        );
    }

    public static List<List<Direction>> ofSoldier(Camp camp) {
        if(camp == Camp.CHO) {
            return List.of(
                    List.of(Direction.WEST),
                    List.of(Direction.EAST),
                    List.of(Direction.NORTH)
            );
        }

        if(camp == Camp.HAN) {
            return List.of(
                    List.of(Direction.WEST),
                    List.of(Direction.EAST),
                    List.of(Direction.SOUTH)
            );
        }

        throw new IllegalArgumentException("[ERROR] Soldier의 이동이 정의되지 않은 Camp 입니다." + camp);
    }
}
