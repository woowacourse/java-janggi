package domain.board;

import domain.Offset;

import java.util.List;

public class Palace {
    private static final List<Offset> CORNER_OFFSETS = List.of(
            new Offset(-1, -1),
            new Offset(-1, 1),
            new Offset(1, -1),
            new Offset(1, 1)
    );

    private final Position center;

    public Palace(Position center) {
        this.center = center;
    }

    public boolean isInPalace(Position position) {
        return position.x() >= center.x() - 1 &&
                position.x() <= center.x() + 1 &&
                position.y() >= center.y() - 1 &&
                position.y() <= center.y() + 1;
    }

    public boolean isCenter(Position position) {
        return center.equals(position);
    }

    public boolean isCorner(Position position) {
        return CORNER_OFFSETS.stream()
                .map(offset -> offset.applyTo(center))
                .anyMatch(position::equals);
    }

    public void requireBothInPalace(Position from, Position to) {
        if (!(isInPalace(from) && isInPalace(to))) {
            throw new IllegalStateException("출발지 또는 목적지가 궁성이 아닙니다.");
        }
    }

    public boolean isValidDiagonalPath(Position from, Position to) {
        return (isCenter(from) && isCorner(to)) || (isCorner(from) && isCenter(to));
    }
}
