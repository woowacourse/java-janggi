package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn implements ChessPiece {
    private static final int[][] ds = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    private final ChessPosition position;
    private final ChessTeam chessTeam;

    public Pawn(ChessPosition position, final ChessTeam chessTeam) {
        this.position = position;
        this.chessTeam = chessTeam;
    }

    public static List<Pawn> initPieces() {
        return List.of(
                new Pawn(new ChessPosition(3, 0), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 2), ChessTeam.RED),
                new Pawn(new ChessPosition(6, 0), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 2), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public List<Path> getAvailablePaths() {
        return Arrays.stream(ds)
                .map(this::getCoordinateNextPositions)
                .filter(coord -> !coord.isEmpty())
                .map(Path::new)
                .toList();
    }

    private List<ChessPosition> getCoordinateNextPositions(int[] d) {
        List<ChessPosition> result = new ArrayList<>();
        final int nextRow = position.row() + d[0];
        final int nextCol = position.column() + d[1];
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
