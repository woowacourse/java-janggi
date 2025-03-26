package janggi.score;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreBoardTest {

    @DisplayName("해당 팀에 점수를 더한다.")
    @Test
    void testAdd() {
        // given
        final ScoreBoard scoreBoard = new ScoreBoard();
        // when
        scoreBoard.add(Team.CHO, new Score(15));
        scoreBoard.add(Team.HAN, new Score(5));
        // then
        assertAll(
                () -> assertThat(scoreBoard.getScore(Team.CHO)).isEqualTo(15),
                () -> assertThat(scoreBoard.getScore(Team.HAN)).isEqualTo(6.5)
        );
    }

    @DisplayName("점수가 높은 팀을 찾는다.")
    @Test
    void testGetWinner() {
        // given
        final ScoreBoard scoreBoard = new ScoreBoard();
        // when
        scoreBoard.add(Team.CHO, new Score(15));
        scoreBoard.add(Team.HAN, new Score(25));
        // then
        assertThat(scoreBoard.getWinner()).isEqualTo(Team.HAN);
    }
}
