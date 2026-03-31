package janggi.domain.piece;

import janggi.domain.movestrategy.CannonStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PieceTest {

    @Test
    @DisplayName("기물이 같은 팀인지 확인한다.")
    void testIsSameTeamPiece() {
        Piece cannonPiece1 = new Piece(Team.HAN, new CannonStrategy());
        Piece cannonPiece2 = new Piece(Team.HAN, new CannonStrategy());
        cannonPiece1.isSameTeam(cannonPiece2);
    }
}
