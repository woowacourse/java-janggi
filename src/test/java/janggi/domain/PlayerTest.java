package janggi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PlayerTest {

    @Test
    @DisplayName("플레이어는 이름과 팀을 기반으로 생성한다")
    void createPlayerWithName() {
        //given
        String name = "flint";
        Team team = Team.RED;

        //when
        Player player = new Player(name, team);

        //then
        assertAll(() -> {
            assertThat(player.getName()).isEqualTo(name);
            assertThat(player.getTeam()).isEqualTo(team);
        });
    }

    @Test
    @DisplayName("플레이어는 점수를 뺄 수 있다")
    void subtractScore() {
        //given
        int scoreValue = 20;
        Player player = new Player("flint", Team.RED, scoreValue);

        //when
        int addValue = 10;
        Score score = new Score(addValue);
        player.subtractScore(score);

        //then
        assertThat(player.getScore()).isEqualTo(new Score(scoreValue - addValue));
    }
}
