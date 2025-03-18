package domain;

import java.util.Arrays;
import java.util.List;

public class Pawn implements ChessPiece{
    private static final int[][] ds = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    private final ChessTeam chessTeam;

    public Pawn(final ChessTeam chessTeam) {
        this.chessTeam = chessTeam;
    }

    @Override
    public List<Path> getAvailablePaths(final ChessPosition chessPosition) {
        return Arrays.stream(ds)
                .map(d -> List.of(new ChessPosition(chessPosition.row() + d[0], chessPosition.column() + d[1])))
                .map(Path::new)
                .toList();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
