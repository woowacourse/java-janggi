package janggi.domain.board;

import static janggi.domain.board.HorseElephantPosition.HEHE;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    public void 특정_위치에_있는_기물이_움직일_수_있는_위치들을_올바르게_반환한다() {
        // given
        BoardDesignPolicy policy = new BoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = new Board(policy);

        // when & then
        assertThatCode(() -> board.canMovePosition(Position.from(4, 5), CHO))
                .doesNotThrowAnyException();
    }

    @Test
    public void 상대_팀의_기물을_움직이려하는_경우에는_오류를_일으킨다() {
        // given
        BoardDesignPolicy policy = new BoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = new Board(policy);

        // when & then
        assertThatThrownBy(() -> board.canMovePosition(Position.from(4, 5), HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치의 기물은 상대 팀의 기물입니다.");
    }

    @Test
    public void 해당_위치에_기물이_없는_경우에는_오류를_일으킨다() {
        // given
        BoardDesignPolicy policy = new BoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = new Board(policy);

        // when & then
        assertThatThrownBy(() -> board.canMovePosition(Position.from(5, 5), CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

}
