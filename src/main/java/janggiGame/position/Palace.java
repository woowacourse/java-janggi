package janggiGame.position;

import java.util.Arrays;
import java.util.Set;

public enum Palace {
    HAN_PALACE(
            Set.of(
                    Position.getInstanceBy(3, 7), Position.getInstanceBy(3, 8), Position.getInstanceBy(3, 9),
                    Position.getInstanceBy(4, 7), Position.getInstanceBy(4, 8), Position.getInstanceBy(4, 9),
                    Position.getInstanceBy(5, 7), Position.getInstanceBy(5, 8), Position.getInstanceBy(5, 9)
            ),
            Position.getInstanceBy(4, 8)
    ),
    CHO_PALACE(
            Set.of(
                    Position.getInstanceBy(3, 0), Position.getInstanceBy(3, 1), Position.getInstanceBy(3, 2),
                    Position.getInstanceBy(4, 0), Position.getInstanceBy(4, 1), Position.getInstanceBy(4, 2),
                    Position.getInstanceBy(5, 0), Position.getInstanceBy(5, 1), Position.getInstanceBy(5, 2)
            ),
            Position.getInstanceBy(4, 1)
    );

    private final Set<Position> area;
    private final Position center;

    Palace(Set<Position> area, Position center) {
        this.area = area;
        this.center = center;
    }

    public static boolean isInPalace(Position position) {
        return Arrays.stream(values()).anyMatch(p -> p.contains(position));
    }

    public static boolean isPalaceDiagonalMove(Position origin, Position destination) {
        return Arrays.stream(values())
                .anyMatch(p -> p.contains(origin) && p.contains(destination) && p.isDiagonalThroughCenter(origin,
                        destination));
    }

    private boolean contains(Position pos) {
        return area.contains(pos);
    }

    private boolean isDiagonalThroughCenter(Position origin, Position destination) {
        int dx = Math.abs(origin.getDx(destination));
        int dy = Math.abs(origin.getDy(destination));

        // 대각선 2칸 이동일 때
        if (dx == 2 && dy == 2) {
            return origin.getDx(center) + destination.getDx(center) == 0 &&
                    origin.getDy(center) + destination.getDy(center) == 0;
        }

        // 대각선 1칸 이동일 때
        return (origin.equals(center) || destination.equals(center)) &&
                dx == 1 && dy == 1;
    }
}
