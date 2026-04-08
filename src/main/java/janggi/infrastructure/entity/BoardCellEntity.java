package janggi.infrastructure.entity;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

public record BoardCellEntity(
    long id,
    int row,
    int column,
    String piece_type,
    String team,
    long game_id
) {

    public static BoardCellEntity from(final long gameId,
        final Position position, final Piece piece) {
        return new BoardCellEntity(0, position.getRow(), position.getColumn(),
            piece.getPieceType().name(), piece.getTeamType().name(), gameId);
    }

    public static BoardCellEntity from(final long id, final long gameId,
        final Position position, final Piece piece) {
        return new BoardCellEntity(id, position.getRow(), position.getColumn(),
            piece.getPieceType().name(), piece.getTeamType().name(), gameId);
    }

}
