package janggi.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Side;
import org.junit.jupiter.api.Test;

class PlayersTest {

    @Test
    void 초기_플레이어_생성_시_두_플레이어의_이름이_같으면_예외가_발생한다() {
        assertThatThrownBy(() -> Players.createInitial(new Name("whale"), new Name("whale")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일한 플레이어 이름을 사용할 수 없습니다.");
    }

    @Test
    void DB_복원용_객체_생성_시_지정한_진영이_현재_턴이_된다() {
        Name choName = new Name("고래");
        Name hanName = new Name("제이콥");

        Players players = Players.createRestored(choName, hanName, Side.HAN);

        assertThat(players.getCurrentSide()).isEqualTo(Side.HAN);
    }
}
