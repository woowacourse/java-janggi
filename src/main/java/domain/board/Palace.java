package domain.board;

import domain.Offset;
import java.util.List;

public class Palace {
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
        List<Offset> cornerOffsets = List.of(
                new Offset(-1, -1),
                new Offset(-1, 1),
                new Offset(1, -1),
                new Offset(1, 1)
        );

        return cornerOffsets.stream()
                .map(offset -> offset.applyTo(center))
                .anyMatch(position::equals);
    }
}
