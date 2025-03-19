package domain;

import java.util.List;

public class Horse implements ChessPiece{

    private final ChessPosition chessPosition;
    private final ChessTeam team;

    public Horse(ChessPosition chessPosition, final ChessTeam team) {
        this.chessPosition = chessPosition;
        this.team = team;
    }

    public static List<Horse> initPieces() {
        return List.of(
                new Horse(new ChessPosition(0, 1), ChessTeam.RED),
                new Horse(new ChessPosition(0, 7), ChessTeam.RED),
                new Horse(new ChessPosition(9, 1), ChessTeam.BLUE),
                new Horse(new ChessPosition(9, 7), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPosition getPosition() {
        return chessPosition;
    }

    @Override
    public List<Path> getAvailablePaths() {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.HORSE;
    }
}
