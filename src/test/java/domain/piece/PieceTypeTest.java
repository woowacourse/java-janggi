package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTypeTest {

    @ParameterizedTest
    @CsvSource({"CHO,72", "HAN,73.5"})
    @DisplayName("각 팀마다 최초 점수를 계산한다.")
    void hanTotalScoreTest(TeamType teamType, double expected) {
        double totalScore = PieceType.getTotalScore(teamType);
        assertThat(totalScore).isEqualTo(expected);
    }
}