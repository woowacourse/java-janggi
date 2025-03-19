package domain.piece;

import domain.Team;
import execptions.JanggiArgumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EmptyPieceTest {
    @Nested
    @DisplayName("기물이 없을 때")
    class TestErrorEmpty {
        @Test
        @DisplayName("팀 여부를 물어보면 예외를 발생한다.")
        void test_isTeam() {
            //given
            final Piece empty = new EmptyPiece();
            //when&then
            Assertions.assertThatThrownBy(() -> empty.hasEqualTeam(Team.HAN))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("기물이 없습니다.");
        }
    }
}
