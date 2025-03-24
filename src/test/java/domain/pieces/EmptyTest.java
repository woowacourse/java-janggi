package domain.pieces;

import domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EmptyTest {

    @Nested
    @DisplayName("기물이 없을 때")
    class TestErrorEmpty {

        @Test
        @DisplayName("팀 여부를 물어보면 예외를 발생한다.")
        void test_isTeam() {
            //given
            final Piece empty = Empty.getInstance();
            //when&then
            Assertions.assertThat(empty.hasEqualTeam(Team.HAN)).isFalse();
            Assertions.assertThat(empty.hasEqualTeam(Team.CHO)).isFalse();
        }
    }
}
