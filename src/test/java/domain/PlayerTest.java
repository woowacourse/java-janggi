package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class PlayerTest {

    @Nested
    @DisplayName("플레이어를 생성할 때")
    class testMakeTeam {

        @Test
        @DisplayName("한나라와 초나라 중 하나의 팀을 갖는다")
        void test_NewTeam() {
            // given
            final Player player = new Player(Team.CHO);

            // when
            final Team team = player.getTeam();

            // then
            assertThat(team).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("한나라는 성공권을 갖는다")
        void test_teamHanIsFirst() {
            // given
            final Player han = new Player(Team.HAN);
            final Player cho = new Player(Team.CHO);

            // when&then
            assertAll(
                    () -> assertThat(han.isFirstAttack()).isFalse(),
                    () -> assertThat(cho.isFirstAttack()).isTrue()
            );

        }
    }
}
