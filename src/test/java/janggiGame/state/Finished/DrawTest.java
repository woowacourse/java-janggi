package janggiGame.state.Finished;

import static org.assertj.core.api.Assertions.assertThat;

import janggiGame.state.GameResult;
import janggiGame.state.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DrawTest {
    @DisplayName("게임 결과를 알맞게 반환한다")
    @Test
    void getGameResult() {
        //given
        State draw = new Draw();

        // when
        GameResult actual = draw.getGameResult();

        // then
        assertThat(actual).isEqualTo(GameResult.DRAW);
    }
}
