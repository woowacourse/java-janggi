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

    public List<Path> getAvailablePaths(final ChessPosition chessPosition) {
        return Arrays.stream(ds)
                .map(d -> List.of(new ChessPosition(chessPosition.row() + d[0], chessPosition.column() + d[1])))
                .map(Path::new)
                .toList();

    }
}
