package domain;

public enum BoardRange {
    START_LOCATION(new Location(1,1)),
    END_LOCATION(new Location(9,10));

    private final Location location;

    BoardRange(Location location) {
        this.location = location;
    }

    public static void validateBoardRange(Location location) {
        if (START_LOCATION.location.isUnder(location) || END_LOCATION.location.isOver(location)) {
            throw new IllegalArgumentException("[ERROR] 보드판 범위 밖에 위치합니다.");
        }
    }
}
