package domain;

import java.util.List;

public class Cannon implements ChessPiece{

    private final ChessPosition position;
    private final ChessTeam team;

    public Cannon(ChessPosition position, final ChessTeam team) {
        this.position = position;
        this.team = team;
    }

    public static List<Cannon> initPieces() {
        return List.of(
                new Cannon(new ChessPosition(2, 1), ChessTeam.RED),
                new Cannon(new ChessPosition(2, 7), ChessTeam.RED),
                new Cannon(new ChessPosition(7, 1), ChessTeam.BLUE),
                new Cannon(new ChessPosition(7, 7), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public List<Path> getAvailablePaths(ChessPiecePositions positions) {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }
}
