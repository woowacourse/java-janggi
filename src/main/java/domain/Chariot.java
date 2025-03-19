package domain;

import java.util.List;

public class Chariot implements ChessPiece {

    private final ChessPosition chessPosition;
    private final ChessTeam team;

    public Chariot(ChessPosition chessPosition, final ChessTeam team) {
        this.chessPosition = chessPosition;
        this.team = team;
    }

    public static List<Chariot> initPieces() {
        return List.of(
                new Chariot(new ChessPosition(0, 0), ChessTeam.RED),
                new Chariot(new ChessPosition(0, 8), ChessTeam.RED),
                new Chariot(new ChessPosition(9, 0), ChessTeam.BLUE),
                new Chariot(new ChessPosition(9, 8), ChessTeam.BLUE)
        );
    }

//    public Chariot move(ChessPosition destination) {
//        new Chariot(team, destination);
//    }

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
        return ChessPieceType.CHARIOT;
    }
}
