package janggi.domain;

import janggi.domain.board.BoardFactory;
import janggi.domain.board.FormationStrategyFactory;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JanggiTest {


    @Test
    void 자신의_턴일때_상대의_기물을_선택하면_예외_처리한다() {
        Janggi janggi = Janggi.start(BoardFactory.create(
                FormationStrategyFactory.from(1),
                FormationStrategyFactory.from(1)));

        assertThatThrownBy(() -> janggi.play(Position.of(9, 0), Position.of(1, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 기물만 선택할 수 있습니다.");
    }

    @Test
    void 자신의_턴일때_정상_입력하면_다음_턴으로_넘어간다() {
        Janggi janggi = Janggi.start(BoardFactory.create(
                FormationStrategyFactory.from(1),
                FormationStrategyFactory.from(1)));
        janggi.play(Position.of(0, 0), Position.of(2, 0));
        assertThat(janggi.currentCamp().isCho()).isFalse();
    }

    @Test
    void 장기_게임이_끝나지_않으면_true를_반환한다() {
        Janggi janggi = Janggi.start(BoardFactory.create(
                FormationStrategyFactory.from(1),
                FormationStrategyFactory.from(1)));
        boolean running = janggi.isRunning();

        assertThat(running).isTrue();
    }

    @Test
    void 장기_게임이_끝나면_false로_변환한다() {
        Janggi janggi = Janggi.start(BoardFactory.create(
                FormationStrategyFactory.from(1),
                FormationStrategyFactory.from(1)));

        janggi.finish();
        boolean running = janggi.isRunning();

        assertThat(running).isFalse();
    }
}
