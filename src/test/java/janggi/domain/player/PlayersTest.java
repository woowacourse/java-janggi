package janggi.domain.player;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PlayersTest {

    @Test
    void 초기_플레이어_생성_시_두_플레이어의_이름이_같으면_예외가_발생한다() {
        assertThatThrownBy(() -> Players.createInitial(new Name("whale"), new Name("whale")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일한 플레이어 이름을 사용할 수 없습니다.");
    }
}
