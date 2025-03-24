package domain.piece;

import domain.board.Position;
import java.util.List;

public abstract class Piece {

    public static final int NO_MOVE = 0;
    public static final int NO_PIECE = 0;
    public static final int ONE_PIECE = 1;

    final PieceType type;
    final PieceColor color;

    protected Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    int countPieceInRoute(List<Piece> piecesInRoute) {
        return (int) piecesInRoute.stream()
                .filter(piece -> piece.color != PieceColor.NONE)
                .count();
    }

    public boolean isSamePiece(Piece other) {
        return type.equals(other.type);
    }

    public boolean isOtherTeam(Piece other) {
        return !color.equals(other.color);
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract boolean isValidDestination(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);

    public abstract boolean canMove(Piece piece, List<Piece> piecesInRoute);

}
