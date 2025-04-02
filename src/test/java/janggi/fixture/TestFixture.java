package janggi.fixture;

import janggi.direction.PieceType;
import janggi.dto.PieceDto;
import janggi.piece.players.Team;

public class TestFixture {

    public static PieceDto makeHanKingPiece() {
        return new PieceDto(Team.HAN, PieceType.KING, 2, 5);
    }

    public static PieceDto makeChoKingPiece() {
        return new PieceDto(Team.CHO, PieceType.KING, 9, 5);
    }
}
