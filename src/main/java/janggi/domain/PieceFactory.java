package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;

public class PieceFactory {

    public static Piece create(PieceType pieceType, TeamType teamType) {
        return pieceType.create(teamType);
    }
}
