package domain;

import java.util.List;

public class King implements ChessPiece{

    private final ChessPosition position;
    private final ChessTeam team;

    public King(ChessPosition position, final ChessTeam team) {
        this.position = position;
        this.team = team;
    }

    public static List<King> initPieces() {
        return List.of(
                new King(new ChessPosition(1, 4), ChessTeam.RED),
                new King(new ChessPosition(8, 4), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public List<Path> getAvailablePaths() {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.KING;
    }
}
