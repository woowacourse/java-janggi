package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JanggiGameTest {


    @Test
    void 말이_정상적으로_이동_한다() {

        // given
        final JanggiGame janggiGame = new JanggiGame(Board.initialize());
        janggiGame.start();

        // when
        // then
        final boolean test = janggiGame.test(3, 2);
        assertThat(test).isTrue();
    }

}
