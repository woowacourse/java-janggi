package domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamColorScoringTest {

    @Test
    void 초나라_기준_점수는_72이다() {
        assertThat(TeamColor.CHO.startingScore()).isEqualTo(MaterialPoints.of(72));
    }

    @Test
    void 한나라_기준_점수는_735이다() {
        assertThat(TeamColor.HAN.startingScore()).isEqualTo(MaterialPoints.of(73.5));
    }
}
