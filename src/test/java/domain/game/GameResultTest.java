package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidGameResultException;
import domain.pieces.Side;
import org.junit.jupiter.api.Test;

class GameResultTest {

    @Test
    void 진행_중인_게임_결과를_생성한다() {
        // given & when
        GameResult gameResult = GameResult.running();

        // then
        assertThat(gameResult.status()).isEqualTo(GameStatus.RUNNING);
        assertThat(gameResult.winner()).isNull();
        assertThat(gameResult.isEnded()).isFalse();
    }

    @Test
    void 종료된_게임_결과를_생성하고_승자를_기록한다() {
        // given & when
        GameResult gameResult = GameResult.ended(Side.HAN);

        // then
        assertThat(gameResult.status()).isEqualTo(GameStatus.ENDED);
        assertThat(gameResult.winner()).isEqualTo(Side.HAN);
        assertThat(gameResult.isEnded()).isTrue();
    }

    @Test
    void 진행_중인_게임에_승자가_있으면_예외를_던진다() {
        // when & then
        assertThatThrownBy(() -> new GameResult(GameStatus.RUNNING, Side.HAN))
            .isInstanceOf(InvalidGameResultException.class)
            .hasMessage(GameErrorMessage.RUNNING_GAME_CANNOT_HAVE_WINNER.message());
    }

    @Test
    void 종료된_게임에_승자가_없으면_예외를_던진다() {
        // when & then
        assertThatThrownBy(() -> new GameResult(GameStatus.ENDED, null))
            .isInstanceOf(InvalidGameResultException.class)
            .hasMessage(GameErrorMessage.ENDED_GAME_MUST_HAVE_WINNER.message());
    }
}
