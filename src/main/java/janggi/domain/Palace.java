package janggi.domain;

import java.util.Arrays;
import java.util.stream.IntStream;

public enum Palace {
    RED(Team.RED, Position.of(2, 5)),
    GREEN(Team.GREEN, Position.of(9, 5)),
    ;

    private final Team team;
    private final Position center;

    Palace(final Team team, final Position center) {
        this.team = team;
        this.center = center;
    }

    public static Palace from(Team team) {
        return Arrays.stream(Palace.values())
                .filter(palace -> palace.team == team)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("궁성 탐색 실패"));
    }

    public boolean isInnerPalace(Position target) {
        return IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap(rowDirection -> IntStream.rangeClosed(-1, 1)
                        .mapToObj(columnDirection -> center.adjust(rowDirection, columnDirection)))
                .anyMatch(position -> position.equals(target));
    }

    public static boolean isInnerSamePalace(Position target, Position other) {
        return Arrays.stream(Palace.values())
                .anyMatch(palace -> palace.isInnerPalace(target) && palace.isInnerPalace(other));
    }

    public static boolean isNotInnerSamePalace(Position target, Position other) {
        return !isInnerSamePalace(target, other);
    }

    public static Palace findPalaceInPosition(Position target) {
        return Arrays.stream(Palace.values())
                .filter(palace -> palace.isInnerPalace(target))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("현재 위치는 궁성 내부가 아닙니다"));
    }

    public boolean isOuterPalace(Position target) {
        return !isInnerPalace(target);
    }

    public boolean isCenterOfPalace(Position position) {
        return this.center.equals(position);
    }
}
