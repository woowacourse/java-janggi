package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

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
}
