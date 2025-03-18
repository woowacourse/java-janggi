package domain;

import java.util.Arrays;
import java.util.List;

public class Pawn extends ChessPiece{
    final int[][] ds = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    public Pawn(final ChessPieceType chessPieceType, final ChessTeam chessTeam) {
        super(chessPieceType, chessTeam);
    }

    public List<Path> getAvailablePaths(final ChessPosition chessPosition) {
        return Arrays.stream(ds)
                .map(d -> List.of(new ChessPosition(chessPosition.row() + d[0], chessPosition.column() + d[1])))
                .map(Path::new)
                .toList();

    }

    @Override
    public List<java.nio.file.Path> getAvailablePaths() {
        return List.of();
    }
}
