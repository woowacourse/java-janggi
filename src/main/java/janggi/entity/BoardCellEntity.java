package janggi.entity;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

public record BoardCellEntity(
    long id,
    int row,
    int column,
    String piece_type,
    String team,
    long board_id
) {

    public static BoardCellEntity from(final long boardId,
        final Position position, final Piece piece) {
        return new BoardCellEntity(0, position.getRow(), position.getColumn(),
            piece.getPieceType().name(), piece.getTeamType().name(), boardId);
    }

    public static BoardCellEntity from(final long id, final long boardId,
        final Position position, final Piece piece) {
        return new BoardCellEntity(id, position.getRow(), position.getColumn(),
            piece.getPieceType().name(), piece.getTeamType().name(), boardId);
    }

}
