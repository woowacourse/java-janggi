package janggi.domain.board;

import java.util.Arrays;

public enum Palace {
    CENTER_RED(Position.of(2,5)), // 빨간색 궁성 중앙
    TOP_LEFT_RED(Position.of(3,6)), TOP_CENTER_RED(Position.of(3, 5)), TOP_RIGHT_RED(Position.of(3, 4)),
    MID_LEFT_RED(Position.of(2, 6)), MID_RIGHT_RED(Position.of(2, 4)),
    BOTTOM_LEFT_RED(Position.of( 1, 6)), BOTTOM_CENTER_RED(Position.of(1, 5)), BOTTOM_RIGHT_RED(Position.of(1, 4)),

    CENTER_BLUE(Position.of(9, 5)), // 파란색 궁성 중앙
    TOP_LEFT_BLUE(Position.of(8, 4)), TOP_CENTER_BLUE(Position.of(8, 5)), TOP_RIGHT_BLUE(Position.of(8, 6)),
    MID_LEFT_BLUE(Position.of(9, 4)), MID_RIGHT_BLUE(Position.of(9, 6)),
    BOTTOM_LEFT_BLUE(Position.of(0, 4)), BOTTOM_CENTER_BLUE(Position.of(0, 5)), BOTTOM_RIGHT_BLUE(Position.of(0, 6));

    private final Position position;

    Palace(Position position) {
        this.position = position;
    }

    public static boolean isInPalace(Position position) {
        return Arrays.stream(Palace.values())
                .anyMatch(palace -> palace.position.equals(position));
    }

    public Position getPosition() {
        return position;
    }
}
