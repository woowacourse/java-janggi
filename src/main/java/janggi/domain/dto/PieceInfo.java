package janggi.domain.dto;

import janggi.domain.Team;
import janggi.domain.piece.PieceType;

public record PieceInfo(
    int x,
    int y,
    Team team,
    PieceType pieceType
) {

}
