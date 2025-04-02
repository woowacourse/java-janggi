package janggi;

import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TeamTest {

    @Test
    @DisplayName("팀을 기준으로 배치될 행을 계산할 수 있다")
    void canCalculateCorrectRow() {
        // given
        final int originalRow = 2;

        // when
        // then
        assertAll(() -> {
            assertThat(Team.decideRow(originalRow, Team.HAN)).isEqualTo(originalRow);
            assertThat(Team.decideRow(originalRow, Team.CHO)).isEqualTo(11 - originalRow);
        });
    }

    @Test
    @DisplayName("문자열 'CHO', 'HAN'을 Team enum 값으로 변환할 수 있다")
    void TeamFrom() {
        // given
        final String cho = "CHO";
        final String han = "HAN";

        // when
        final Team choTeam = Team.from(cho);
        final Team hanTeam = Team.from(han);

        // then
        assertAll(() -> {
            assertThat(choTeam).isEqualTo(Team.CHO);
            assertThat(hanTeam).isEqualTo(Team.HAN);
        });
    }
}
