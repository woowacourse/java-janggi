package janggi.fixture;

import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.domain.players.Team;

public class TestFixture {

    public static PieceDto makeHanKingPiece() {
        return new PieceDto(Team.HAN, Piece.KING, 2, 5);
    }

    public static PieceDto makeChoKingPiece() {
        return new PieceDto(Team.CHO, Piece.KING, 9, 5);
    }
}
