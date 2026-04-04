package dto;

import domain.state.Side;
import domain.piece.Piece;
import domain.piece.PieceType;

public class PieceDto {

    private final PieceType pieceType;
    private final Side side;

    public PieceDto(Piece piece) {
        this.pieceType = piece.getType();
        this.side = piece.getSide();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Side getSide() {
        return side;
    }
}
