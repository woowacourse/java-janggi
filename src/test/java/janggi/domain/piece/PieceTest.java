package janggi.domain.piece;

import janggi.domain.piece.PieceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PieceTest {

    @Test
    @DisplayName("기물이 같은 팀인지 확인한다.")
    void testIsSameTeamPiece() {
        Piece cannonPiece1 = PieceFactory.createCannon(Team.HAN);
        Piece cannonPiece2 = PieceFactory.createCannon(Team.HAN);
        cannonPiece1.isSameTeam(cannonPiece2);
    }
}
