package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Nested
    class ValidCases {

        @DisplayName("기물의 팀인지 확인한다.")
        @Test
        void isMyTeam() {
            // given
            Piece piece = new Cannon(Team.RED);

            //when & then
            assertAll(
                    () -> assertThat(piece.isMyTeam(Team.RED)).isTrue(),
                    () -> assertThat(piece.isMyTeam(Team.GREEN)).isFalse()
            );
        }
    }
}
