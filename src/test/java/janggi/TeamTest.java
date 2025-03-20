package janggi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TeamTest {

    @Test
    @DisplayName("팀을 기준으로 배치될 행을 계산할 수  있다")
    void canCalculateCorrectRow() {
        // given
        int originalRow = 2;

        // when
        // then
        assertAll(() -> {
            assertThat(Team.decideRow(originalRow, Team.RED)).isEqualTo(originalRow);
            assertThat(Team.decideRow(originalRow, Team.GREEN)).isEqualTo(11 - originalRow);
        });
    }
}
