package team.janggi.domain;

import java.util.List;
import java.util.Set;
import team.janggi.domain.piece.strategy.PalaceInfo;

public final class Palace {
    public static final Position HAN_KING_POSITION = new Position(4, 1);
    public static final Set<Position> HAN_POSITION = Set.of(
            new Position(3, 0), new Position(4, 0), new Position(5, 0),
            new Position(3, 1), HAN_KING_POSITION,        new Position(5, 1),
            new Position(3, 2), new Position(4, 2), new Position(5, 2)
    );
    public static final Set<Position> HAN_DIAGONAL_POSITIONS1 =
            Set.of(new Position(3, 0), HAN_KING_POSITION, new Position(5, 2));
    public static final Set<Position> HAN_DIAGONAL_POSITIONS2 =
            Set.of(new Position(5, 0), HAN_KING_POSITION, new Position(3, 2));



    public static final Position CHO_KING_POSITION = new Position(4, 8);
    public static final Set<Position> CHO_POSITION = Set.of(
            new Position(3, 7), new Position(4, 7), new Position(5, 7),
            new Position(3, 8), CHO_KING_POSITION, new Position(5, 8),
            new Position(3, 9), new Position(4, 9), new Position(5, 9)
    );
    public static final Set<Position> CHO_DIAGONAL_POSITIONS1 =
            Set.of(new Position(3, 7), CHO_KING_POSITION, new Position(5, 9));
    public static final Set<Position> CHO_DIAGONAL_POSITIONS2 =
            Set.of(new Position(5, 7), CHO_KING_POSITION, new Position(3, 9));

    private Palace() {}

    public static boolean isInPalace(Position position) {
        return isInHanPalace(position) || isInChoPalace(position);
    }

    public static boolean isInHanPalace(Position position) {
        return HAN_POSITION.contains(position);
    }

    public static boolean isInChoPalace(Position position) {
        return CHO_POSITION.contains(position);
    }

    public static boolean isChoDiagonalPath(List<Position> path) {
        for (Position position : path) {
            if (!isChoDiagonalPosition(position)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isHanDiagonalPath(List<Position> path) {
        for (Position position : path) {
            if (!isHanDiagonalPosition(position)) {
                return false;
            }
        }

        return true;
    }


    private static boolean isHanDiagonalPosition(Position position) {
        return HAN_DIAGONAL_POSITIONS1.contains(position) || HAN_DIAGONAL_POSITIONS2.contains(position);
    }

    private static boolean isChoDiagonalPosition(Position position) {
        return CHO_DIAGONAL_POSITIONS1.contains(position) || CHO_DIAGONAL_POSITIONS2.contains(position);
    }

    public static PalaceInfo getChoPalaceInfo() {
        return new PalaceInfo(CHO_POSITION, CHO_DIAGONAL_POSITIONS1, CHO_DIAGONAL_POSITIONS2);
    }

    public static PalaceInfo getHanPalaceInfo() {
        return new PalaceInfo(HAN_POSITION, HAN_DIAGONAL_POSITIONS1, HAN_DIAGONAL_POSITIONS2);
    }
}
