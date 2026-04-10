package janggi.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PieceTest {

    @Test
    @DisplayName("기물이 같은 팀인지 확인한다.")
    void testIsSameTeamPiece() {
        // given
        Piece cannonPiece1 = PieceFactory.createCannon(Team.HAN);
        Piece cannonPiece2 = PieceFactory.createCannon(Team.HAN);

        // when & then
        assertThat(cannonPiece1.isSameTeam(cannonPiece2)).isTrue();
    }
}
