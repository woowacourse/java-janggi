package janggi.domain.position;

import java.util.Map;

public class Castle {
    private static final Map<Position, CastleType> castleArea = Map.ofEntries(
            Map.entry(new Position(3, 0), CastleType.BOTTOM_CORNER),
            Map.entry(new Position(5, 0), CastleType.BOTTOM_CORNER),
            Map.entry(new Position(4, 1), CastleType.BOTTOM_CENTER),
            Map.entry(new Position(3, 2), CastleType.BOTTOM_CORNER),
            Map.entry(new Position(5, 2), CastleType.BOTTOM_CORNER),
            Map.entry(new Position(4, 0), CastleType.BOTTOM_SIDE),
            Map.entry(new Position(3, 1), CastleType.BOTTOM_SIDE),
            Map.entry(new Position(5, 1), CastleType.BOTTOM_SIDE),
            Map.entry(new Position(4, 2), CastleType.BOTTOM_SIDE),
            Map.entry(new Position(3, 7), CastleType.TOP_CORNER),
            Map.entry(new Position(5, 7), CastleType.TOP_CORNER),
            Map.entry(new Position(4, 8), CastleType.TOP_CENTER),
            Map.entry(new Position(3, 9), CastleType.TOP_CORNER),
            Map.entry(new Position(5, 9), CastleType.TOP_CORNER),
            Map.entry(new Position(4, 7), CastleType.TOP_SIDE),
            Map.entry(new Position(3, 8), CastleType.TOP_SIDE),
            Map.entry(new Position(5, 8), CastleType.TOP_SIDE),
            Map.entry(new Position(4, 9), CastleType.TOP_SIDE)
    );

    private Castle() {
    }

    public static boolean isInsideCastle(Position position) {
        return castleArea.containsKey(position);
    }

    public static boolean isSameCastle(Position position, Position other) {
        if (!(isInsideCastle(position) && isInsideCastle(other))) {
            return false;
        }

        CastleType castleType = castleArea.get(position);
        CastleType otherCastleType = castleArea.get(other);

        return castleType.isSameTeam(otherCastleType);
    }

    public static boolean isCastleSide(Position position) {
        if(!isInsideCastle(position)) {
            throw new IllegalArgumentException("해당 위치는 궁성이 아닙니다.");
        }

        CastleType castleType = castleArea.get(position);

        return castleType.isSide();
    }
}
