package janggi.dao;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Position;

public record PieceVO(Long id, Long boardId, String type, String camp, Integer x, Integer y) {

    public static PieceVO of(Long boardId, Position position, Piece piece) {
        return new PieceVO(
                null,
                boardId,
                piece.getType().name(),
                piece.getCamp().name(),
                position.x(),
                position.y()
        );
    }

    public Piece toPiece() {
        Type type = Type.valueOf(this.type);
        Camp camp = Camp.valueOf(this.camp);
        return type.createPiece(camp);
    }

    public Position toPosition() {
        return Position.of(x, y);
    }
}
