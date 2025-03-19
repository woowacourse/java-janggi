package domain;

import java.util.ArrayList;
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
                .map(d -> temp(d, chessPosition))
                .filter(coord -> !coord.isEmpty())
                .map(Path::new)
                .toList();
    }

    private List<ChessPosition> temp(int[] d, ChessPosition chessPosition) {
        List<ChessPosition> result = new ArrayList<>();
        final int nextRow = chessPosition.row() + d[0];
        final int nextCol = chessPosition.column() + d[1];
        if (ChessPosition.isValid(nextRow, nextCol)) {
            result.add(new ChessPosition(nextRow, nextCol));
        }
        return result;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
