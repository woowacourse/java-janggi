package janggi.dao.piece;

public record PieceEntity(
        Long id,
        Long gameId,
        String pieceType,
        int positionRow,
        int positionColumn,
        String team
) { }
