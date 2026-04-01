package domain;

import java.util.ArrayList;
import java.util.List;

public enum Palace {
    CHO_PALACE(
            List.of(new Position(3, 0), new Position(5, 0), new Position(4, 1),
                    new Position(3, 2), new Position(5, 2)),
            List.of(new Position(4, 0), new Position(3, 1), new Position(5, 1), new Position(4, 2))),
    HAN_PALACE(
            List.of(new Position(3, 7), new Position(5, 7), new Position(4, 8),
                    new Position(3, 9), new Position(5, 9)),
            List.of(new Position(4, 7), new Position(3, 8), new Position(5, 8), new Position(4, 9))),
    ;

    private final List<Position> diagonalPositions;
    private final List<Position> normalPositions;

    Palace(List<Position> diagonalPositions, List<Position> normalPositions) {
        this.diagonalPositions = diagonalPositions;
        this.normalPositions = normalPositions;
    }

    public static Palace from(Country country) {
        if (country == Country.CHO) {
            return CHO_PALACE;
        }
        return HAN_PALACE;
    }

    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>(diagonalPositions);
        positions.addAll(new ArrayList<>(normalPositions));
        return positions;
    }

    public static boolean isSamePalace(Position from, Position to) {
        if (CHO_PALACE.getPositions().contains(from) && CHO_PALACE.getPositions().contains(to)) {
            return true;
        }
        return HAN_PALACE.getPositions().contains(from) && HAN_PALACE.getPositions().contains(to);
    }

    public static boolean canDiagonal(Position from) {
        return CHO_PALACE.diagonalPositions.contains(from) || HAN_PALACE.diagonalPositions.contains(from);
    }
}
