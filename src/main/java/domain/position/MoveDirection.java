package domain.position;

import domain.piece.Camp;

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

    public static List<Direction> ofDiagonal() {
        return List.of(
                Direction.NORTHWEST,
                Direction.NORTHEAST,
                Direction.SOUTHEAST,
                Direction.SOUTHWEST
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

    public static List<Direction> ofAllAround() {
        return List.of(
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST,
                Direction.NORTHWEST,
                Direction.NORTHEAST,
                Direction.SOUTHWEST,
                Direction.SOUTHEAST
        );
    }

    public static List<Direction> ofSoldier(Camp camp) {
        if(camp == Camp.CHO) {
            return List.of(
                    Direction.WEST,
                    Direction.EAST,
                    Direction.NORTH
            );
        }

        if(camp == Camp.HAN) {
            return List.of(
                    Direction.WEST,
                    Direction.EAST,
                    Direction.SOUTH
            );
        }

        throw new IllegalArgumentException("[ERROR] Soldier의 이동이 정의되지 않은 Camp 입니다." + camp);
    }

    public static List<Direction> ofSoldierInPalace(Camp camp) {
        if(camp == Camp.CHO) {
            return List.of(
                    Direction.WEST,
                    Direction.EAST,
                    Direction.NORTH,
                    Direction.NORTHEAST,
                    Direction.NORTHWEST
            );
        }

        if(camp == Camp.HAN) {
            return List.of(
                    Direction.WEST,
                    Direction.EAST,
                    Direction.SOUTH,
                    Direction.SOUTHEAST,
                    Direction.SOUTHWEST
            );
        }

        throw new IllegalArgumentException("[ERROR] Soldier의 이동이 정의되지 않은 Camp 입니다." + camp);
    }
}
