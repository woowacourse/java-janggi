package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidGameResultException;
import domain.pieces.Side;
import org.junit.jupiter.api.Test;

class GameScoreTest {

    @Test
    void 초_점수가_더_높으면_초가_승자다() {
        // given
        GameScore gameScore = new GameScore(new Score(13), new Score(10));
        // when & then
        assertThat(gameScore.winner()).isEqualTo(Side.CHO);
    }

    @Test
    void 한_점수가_더_높으면_한이_승자다() {
        // given
        GameScore gameScore = new GameScore(new Score(13), new Score(23));
        // when & then
        assertThat(gameScore.winner()).isEqualTo(Side.HAN);
    }

    @Test
    void 동점인_점수는_승자를_결정할_때_예외를_던진다() {
        // given
        GameScore gameScore = new GameScore(new Score(13), new Score(13));

        // when & then
        assertThatThrownBy(gameScore::winner)
                .isInstanceOf(InvalidGameResultException.class)
                .hasMessage(GameErrorMessage.GAME_SCORE_CANNOT_BE_TIED.message());
    }
}
