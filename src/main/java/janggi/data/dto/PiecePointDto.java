package janggi.data.dto;

import janggi.domain.board.Dynasty;
import janggi.domain.piece.PieceType;

public record PiecePointDto(
        PieceType pieceType,
        Dynasty dynasty,
        int x,
        int y
) {
}
