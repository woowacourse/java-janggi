package janggi.domain.piece;

import janggi.domain.board.Position;

import janggi.domain.moveRule.MoveRule;
import java.util.List;

public abstract class Piece {
    final PieceColor color;
    final PieceType type;
    final MoveRule moveRule;

    Piece(PieceColor color, PieceType type, MoveRule moveRule) {
        this.color = color;
        this.type = type;
        this.moveRule = moveRule;
    }

    public abstract boolean isValidDestination(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);

    public boolean canMove(Piece sourcePiece, Piece destinationPiece, List<Piece> piecesInRoute) {
        return moveRule.canMove(sourcePiece, destinationPiece, piecesInRoute);
    }

    public int countPieceInRoute(List<Piece> piecesInRoute) {
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

    public PieceType getType() {
        return type;
    }
}
