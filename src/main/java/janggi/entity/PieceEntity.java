package janggi.entity;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record PieceEntity(
        int row,
        int column,
        String dynasty,
        String type
) {

    public static PieceEntity from(Position position, Piece piece) {
        return new PieceEntity(
                position.row().row(),
                position.column().column(),
                piece.dynasty().name(),
                piece.pieceType().name()
        );
    }

    public static List<PieceEntity> from(Map<Position, Piece> pieces) {
        List<PieceEntity> pieceEntities = new ArrayList<>();
        pieces.forEach((position, piece) ->
                pieceEntities.add(PieceEntity.from(position, piece)));
        return pieceEntities;
    }

}
