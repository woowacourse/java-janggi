package janggi.domain.player;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Side;
import janggi.domain.board.Formation;
import org.junit.jupiter.api.Test;

class PlayersTest {

    @Test
    void 초기_플레이어_생성_시_두_플레이어의_이름이_같으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Players(new Player(new Name("whale"), Side.CHO, Formation.INNER_ELEPHANT), new Player(new Name("whale"), Side.HAN, Formation.INNER_ELEPHANT)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일한 플레이어 이름을 사용할 수 없습니다.");
    }
}
