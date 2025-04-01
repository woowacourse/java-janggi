package janggi.domain;

import janggi.domain.piece.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum
Palace {
    HAN_DOWN_LEFT(new Position(3, 0)),
    HAN_DOWN_MIDDLE(new Position(4, 0)),
    HAN_DOWN_RIGHT(new Position(5, 0)),
    HAN_MIDDLE_LEFT(new Position(3, 1)),
    HAN_MIDDLE_MIDDLE(new Position(4, 1)),
    HAN_MIDDLE_RIGHT(new Position(5, 1)),
    HAN_UP_LEFT(new Position(3, 2)),
    HAN_UP_MIDDLE(new Position(4, 2)),
    HAN_UP_RIGHT(new Position(5, 2)),

    CHO_DOWN_LEFT(new Position(3, 7)),
    CHO_DOWN_MIDDLE(new Position(4, 7)),
    CHO_DOWN_RIGHT(new Position(5, 7)),
    CHO_MIDDLE_LEFT(new Position(3, 8)),
    CHO_MIDDLE_MIDDLE(new Position(4, 8)),
    CHO_MIDDLE_RIGHT(new Position(5, 8)),
    CHO_UP_LEFT(new Position(3, 9)),
    CHO_UP_MIDDLE(new Position(4, 9)),
    CHO_UP_RIGHT(new Position(5, 9));

    private static final Map<Palace, List<Palace>> DIAGONAL_POSITIONS = Map.ofEntries(
        Map.entry(HAN_DOWN_LEFT, List.of(HAN_MIDDLE_MIDDLE, HAN_UP_RIGHT)),
        Map.entry(HAN_DOWN_MIDDLE, List.of()),
        Map.entry(HAN_DOWN_RIGHT, List.of(HAN_MIDDLE_MIDDLE, HAN_UP_LEFT)),
        Map.entry(HAN_MIDDLE_LEFT, List.of()),
        Map.entry(HAN_MIDDLE_MIDDLE, List.of(HAN_DOWN_LEFT, HAN_DOWN_RIGHT, HAN_UP_LEFT, HAN_UP_RIGHT)),
        Map.entry(HAN_MIDDLE_RIGHT, List.of()),
        Map.entry(HAN_UP_LEFT, List.of(HAN_MIDDLE_MIDDLE, HAN_DOWN_RIGHT)),
        Map.entry(HAN_UP_MIDDLE, List.of()),
        Map.entry(HAN_UP_RIGHT, List.of(HAN_MIDDLE_MIDDLE, HAN_DOWN_LEFT)),
        Map.entry(CHO_DOWN_LEFT, List.of(CHO_MIDDLE_MIDDLE, CHO_UP_RIGHT)),
        Map.entry(CHO_DOWN_MIDDLE, List.of()),
        Map.entry(CHO_DOWN_RIGHT, List.of(CHO_MIDDLE_MIDDLE, CHO_UP_LEFT)),
        Map.entry(CHO_MIDDLE_LEFT, List.of()),
        Map.entry(CHO_MIDDLE_MIDDLE, List.of(CHO_DOWN_LEFT, CHO_DOWN_RIGHT, CHO_UP_LEFT, CHO_UP_RIGHT)),
        Map.entry(CHO_MIDDLE_RIGHT, List.of()),
        Map.entry(CHO_UP_LEFT, List.of(CHO_MIDDLE_MIDDLE, CHO_DOWN_RIGHT)),
        Map.entry(CHO_UP_MIDDLE, List.of()),
        Map.entry(CHO_UP_RIGHT, List.of(CHO_MIDDLE_MIDDLE, CHO_DOWN_LEFT))
    );

    private final Position position;

    Palace(Position position) {
        this.position = position;
    }

    public static Palace fromPosition(Position position) {
        return Arrays.stream(values())
            .filter(hanPalace -> hanPalace.position.equals(position))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("궁성 안에 있는 좌표가 아닙니다."));
    }

    public static boolean isInPalace(Position position) {
        return Arrays.stream(values())
            .anyMatch(palace -> palace.getPosition().equals(position));
    }

    public Position getPosition() {
        return position;
    }

    public List<Position> getDiagonalPositions() {
        return DIAGONAL_POSITIONS.get(this).stream()
            .map(Palace::getPosition)
            .toList();
    }
}

