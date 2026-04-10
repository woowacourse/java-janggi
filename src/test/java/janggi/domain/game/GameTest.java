package janggi.domain.game;

import static janggi.domain.board.HorseElephantPosition.HEHE;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    public void 선택된_기물이_움직일_수_있는_위치가_없다면_오류를_일으킨다() {
        // given
        Game game = Game.initGame(Map.of(CHO, HEHE, HAN, HEHE));
        Position from = Position.from(3, 2);

        // when & then
        assertThatThrownBy(() -> game.placeablePositions(from))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("선택된 기물이 이동할 수 있는 위치가 없습니다.");
    }

}
