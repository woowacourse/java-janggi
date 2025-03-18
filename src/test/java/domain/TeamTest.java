package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamTest {

    @Test
    @DisplayName("HAN 팀은 거리를 반대로 적용한다.")
    void test1() {
        // given
        Team hanTeam = Team.HAN;
        int distance = 1;

        // when
        int result = hanTeam.applyDirection(distance);

        // then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("CHO 팀은 거리를 그대로 적용한다.")
    void test2() {
        // given
        Team choTeam = Team.CHO;
        int distance = 1;

        // when
        int result = choTeam.applyDirection(distance);

        // then
        assertThat(result).isEqualTo(1);
    }
}
