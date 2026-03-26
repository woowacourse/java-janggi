package janggi.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PieceTest {

    @Test
    @DisplayName("기물이 같은 팀인지 확인한다.")
    void testIsSameTeamPiece() {
        CannonPiece cannonPiece1 = new CannonPiece(Team.HAN);
        CannonPiece cannonPiece2 = new CannonPiece(Team.HAN);
        cannonPiece1.isSameTeam(cannonPiece2);
    }
}
