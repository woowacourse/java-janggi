package janggi.domain;

import janggi.domain.piece.PieceType;

public record PieceInfo(
    int x,
    int y,
    Team team,
    PieceType pieceType
) {

}
