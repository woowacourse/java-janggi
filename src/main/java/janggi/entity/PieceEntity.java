package janggi.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PieceEntity(
        int row,
        int column,
        String dynasty,
        String type
) {

    public static PieceEntity toEntity(int row, int column, String dynasty, String type) {
        return new PieceEntity(row, column, dynasty, type);
    }

    public static PieceEntity toEntity(Position position, Piece piece) {
        return new PieceEntity(
                position.row().row(),
                position.column().column(),
                piece.dynasty().name(),
                piece.pieceType().name()
        );
    }

    public static List<PieceEntity> toEntities(Map<Position, Piece> pieces) {
        List<PieceEntity> pieceEntities = new ArrayList<>();
        pieces.forEach((position, piece) ->
                pieceEntities.add(PieceEntity.toEntity(position, piece)));
        return pieceEntities;
    }

    public static Map<Position, Piece> toDomains(List<PieceEntity> entities) {
        Map<Position, Piece> pieces = new HashMap<>();
        entities.forEach(entity -> {
            Position position = Position.from(entity.row, entity.column);
            Piece piece = new Piece(Dynasty.valueOf(entity.dynasty), PieceType.valueOf(entity.type));
            pieces.put(position, piece);
        });
        return pieces;
    }

}
