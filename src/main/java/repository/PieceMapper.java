package repository;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;

final class PieceMapper {

    private PieceMapper() {
    }

    static PieceEntity toEntity(long gameId, Intersection position, Piece piece) {
        return new PieceEntity(
                gameId,
                position.row(),
                position.file(),
                piece.getType().name(),
                piece.getSide().name()
        );
    }

    static Piece toPiece(String typeName, String sideName) {
        return Piece.of(
                PieceType.from(typeName),
                Side.valueOf(sideName)
        );
    }
}
