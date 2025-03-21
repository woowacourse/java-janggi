package domain.piece;

import domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PieceTest {

    @DisplayName("두 기물의 팀이 같은지 판단한다")
    @ParameterizedTest
    @CsvSource({
            "RED, RED, true", "RED, BLUE, false "
    })
    void test(Team team1, Team team2, boolean expected) {
        // given
        Piece piece1 = new Horse(team1);
        Piece piece2 = new Horse(team2);

        // when
        boolean actual = piece1.compareTeam(piece2);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
