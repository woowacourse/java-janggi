package janggi.dao;

import janggi.domain.Team;
import janggi.domain.piece.PieceType;

public record PieceEntity(
    long gameId,
    int x,
    int y,
    Team team,
    PieceType pieceType
) {

}
