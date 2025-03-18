package domain;

import java.util.Arrays;
import java.util.List;

public class Pawn {
    final int[][] ds = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    public List<Path> getAvailablePaths(final Position position) {
        return Arrays.stream(ds)
                .map(d -> List.of(new Position(position.row() + d[0], position.colum() + d[1])))
                .map(Path::new)
                .toList();

    }
}
