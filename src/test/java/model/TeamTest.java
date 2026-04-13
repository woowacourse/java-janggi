package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void 한나라_다음_차례는_초나라다() {
        // given
        Team han = Team.HAN;

        // when
        Team next = han.opposite();

        // then
        assertThat(next).isEqualTo(Team.CHO);
    }

    @Test
    void 초나라_다음_차례는_한나라다() {
        // given
        Team cho = Team.CHO;

        // when
        Team next = cho.opposite();

        // then
        assertThat(next).isEqualTo(Team.HAN);
    }
}