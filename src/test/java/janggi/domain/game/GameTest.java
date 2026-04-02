package janggi.domain.game;

import static janggi.domain.board.HorseElephantPosition.HEHE;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.dynasty.Dynasty;
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
        assertThatThrownBy(() -> game.canMovePosition(from))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("선택된 기물이 이동할 수 있는 위치가 없습니다.");
    }

    @Test
    public void 두_장이_살아있으면_게임은_계속된다() {
        // given
        Game game = Game.initGame(Map.of(CHO, HEHE, HAN, HEHE));

        // when
        boolean finished = game.isFinished();

        // then
        assertThat(finished).isFalse();
    }

    @Test
    public void 장이_없으면_게임이_끝난다() {
        // given
        Game game = Game.initGame(Map.of(CHO, HEHE, HAN, HEHE));

        // when: 초나라 장을 잡는 수
        game.movePiece(Position.from(1, 1), Position.from(2, 1));
        game.movePiece(Position.from(10, 2), Position.from(8, 3));

        game.movePiece(Position.from(4, 5), Position.from(4, 6));
        game.movePiece(Position.from(8, 2), Position.from(8, 5));

        game.movePiece(Position.from(2, 5), Position.from(3, 5));
        game.movePiece(Position.from(8, 5), Position.from(3, 5));

        boolean finished = game.isFinished();

        // then
        assertThat(finished).isTrue();
    }

    @Test
    public void 장이_잡힌_나라는_패배이다() {
        // given
        Game game = Game.initGame(Map.of(CHO, HEHE, HAN, HEHE));

        // when: 초나라 장을 잡는 수
        game.movePiece(Position.from(1, 1), Position.from(2, 1));
        game.movePiece(Position.from(10, 2), Position.from(8, 3));

        game.movePiece(Position.from(4, 5), Position.from(4, 6));
        game.movePiece(Position.from(8, 2), Position.from(8, 5));

        game.movePiece(Position.from(2, 5), Position.from(3, 5));
        game.movePiece(Position.from(8, 5), Position.from(3, 5));

        Dynasty winner = game.judgeWinner();

        // then
        assertThat(winner).isEqualTo(HAN);
    }

}
