package domain.piece;

import domain.board.Position;

import java.util.List;

public abstract class Piece {
    final PieceColor color;
    final PieceType type;

    Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    int countPieceInRoute(List<Piece> piecesInRoute) {
        return (int) piecesInRoute.stream()
                .filter(piece -> piece.color != PieceColor.NONE)
                .count();
    }

    public boolean isSamePieceType(Piece other) {
        return this.type == other.type;
    }

    public boolean isPieceType(PieceType pieceType) {
        return this.type == pieceType;
    }

    public boolean isOtherTeam(Piece other) {
        return this.color != other.color;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract boolean isValidDestination(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);

    public abstract boolean canMove(Piece piece, List<Piece> piecesInRoute);

    public PieceType getType() {
        return type;
    }
}
