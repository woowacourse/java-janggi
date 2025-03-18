package domain;

import java.util.Arrays;
import java.util.List;

public class Pawn {

    public List<Position> getAvailablePositions(final Position position) {
        final int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        return Arrays.stream(ds)
                .map(d -> new Position(position.row() + d[0], position.colum() + d[1]))
                .toList();
    }
}
