package game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    @DisplayName("팀의 이름에 해당하는 팀을 반환한다.")
    void test1() {
        //given
        String name = "red";

        //when
        Team team = Team.fromName(name);

        //then
        assertThat(team).isEqualTo(Team.RED);
    }

    @Test
    @DisplayName("반대되는 팀을 정상적으로 반환한다.")
    void test2() {
        //given
        Team red = Team.RED;

        //when
        Team opponent = red.findOpponent();

        //then
        assertThat(opponent).isEqualTo(Team.GREEN);
    }

    @Test
    @DisplayName("팀의 최종 스코어를 정상적으로 반환한다.")
    void test3() {
        //given
        Team red = Team.RED;
        double redInitialScore = 73.5;
        int catchScore = 10;

        //when & then
        assertThat(red.calculateFinalScore(10)).isEqualTo(redInitialScore - catchScore);
    }
}
