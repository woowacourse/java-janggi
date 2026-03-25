package domain;

import java.util.List;

public enum MaSangPosition {
    MA_SANG_SANG_MA(1,
            List.of(new Position(1, 2), new Position(1, 8)), // 초 마
            List.of(new Position(1, 3), new Position(1, 7)), // 초 상
            List.of(new Position(10, 2), new Position(10, 8)), // 한 마
            List.of(new Position(10, 3), new Position(10, 7))  // 한 상
    ),

    MA_SANG_MA_SANG(2,
            List.of(new Position(1, 2), new Position(1, 7)), // 초 마
            List.of(new Position(1, 3), new Position(1, 8)), // 초 상
            List.of(new Position(10, 3), new Position(10, 8)), // 한 마
            List.of(new Position(10, 2), new Position(10, 7))  // 한 상
    ),

    SANG_MA_SANG_MA(3,
            List.of(new Position(1, 3), new Position(1, 8)), // 초 마
            List.of(new Position(1, 2), new Position(1, 7)), // 초 상
            List.of(new Position(10, 2), new Position(10, 7)), // 한 마
            List.of(new Position(10, 3), new Position(10, 8))  // 한 상
    ),

    SANG_MA_MA_SANG(4,
            List.of(new Position(1, 3), new Position(1, 7)), // 초 마
            List.of(new Position(1, 2), new Position(1, 8)), // 초 상
            List.of(new Position(10, 3), new Position(10, 7)), // 한 마
            List.of(new Position(10, 2), new Position(10, 8))  // 한 상
    );

    private final int command;
    private final List<Position> choMa;
    private final List<Position> choSang;
    private final List<Position> hanMa;
    private final List<Position> hanSang;

    MaSangPosition(int command,
                   List<Position> choMa, List<Position> choSang,
                   List<Position> hanMa, List<Position> hanSang) {
        this.command = command;
        this.choMa = choMa;
        this.choSang = choSang;
        this.hanMa = hanMa;
        this.hanSang = hanSang;
    }

    public static List<Position> getChoMaPosition(int number) {
        return MaSangPosition.values()[number - 1].choMa;
    }

    public static List<Position> getChoSangPosition(int number) {
        return MaSangPosition.values()[number - 1].choSang;
    }

    public static List<Position> getHanMaPosition(int number) {
        return MaSangPosition.values()[number - 1].hanMa;
    }

    public static List<Position> getHanSangPosition(int number) {
        return MaSangPosition.values()[number - 1].hanSang;
    }
}