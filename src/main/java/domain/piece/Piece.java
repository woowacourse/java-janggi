package domain.piece;

import domain.board.Position;

import java.util.List;

public abstract class Piece {
    final PieceColor color;

    Piece(PieceColor color) {
        this.color = color;
    }

    public boolean isOtherTeam(Piece other) {
        return this.color != other.color;
    }

    public abstract boolean isValidDestination(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);

    public abstract boolean canMove(Piece piece, List<Piece> piecesInRoute);

    int countPieceInRoute(List<Piece> piecesInRoute) {
        return (int) piecesInRoute.stream()
                .filter(piece -> piece.color != PieceColor.NONE)
                .count();
    }

    boolean isSamePiece(Piece other) {
        return this.getClass() == other.getClass();
    }

    public PieceColor getColor() {
        return color;
    }
}
