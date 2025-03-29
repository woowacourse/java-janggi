package domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class PlayerTest {

    @Nested
    @DisplayName("플레이어를 생성할 때")
    class TestMakeTeam {

        @Test
        @DisplayName("한나라와 초나라 중 하나의 팀을 갖는다")
        void test_NewTeam() {
            // given
            final Player player = new Player(1, Team.CHO);

            // when
            final Team team = player.getTeam();

            // then
            assertThat(team).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("초기 상태에서는 초나라가 턴을 갖는다")
        void test_teamHanIsFirst() {
            // given
            final Player han = new Player(1, Team.HAN);
            final Player cho = new Player(2, Team.CHO);

            // when&then
            assertAll(
                    () -> assertThat(han.isTurn()).isFalse(),
                    () -> assertThat(cho.isTurn()).isTrue()
            );

        }

        @Test
        @DisplayName("턴을 일괄적으로 교체할 수 있다.")
        void test_switchTurn() {
            // given
            final Player han = new Player(1, Team.HAN);
            final Player cho = new Player(2, Team.CHO);
            final List<Player> players = List.of(han, cho);

            // when
            players.forEach(Player::switchTurn);

            // then
            assertAll(
                    () -> assertThat(han.isTurn()).isTrue(),
                    () -> assertThat(cho.isTurn()).isFalse()
            );

        }
    }
}
