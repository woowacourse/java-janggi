package janggi.domain.piece;

import janggi.domain.team.TeamType;

public class PieceFactory {

    public static Piece create(PieceType pieceType, TeamType teamType) {
        return pieceType.createPiece(teamType);
    }
}
