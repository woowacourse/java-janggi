package domain.piece;

import domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PieceTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "HAN, CHO, false",
            "CHO, HAN, false",
            "CHO, CHO, true"
    })
    void 두_기물의_팀이_같은지_판단한다(Team team1, Team team2, boolean expected) {
        // given
        Piece piece1 = new Ma(team1);
        Piece piece2 = new Ma(team2);

        // when
        boolean actual = piece1.isTeam(piece2);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "HAN, CHO, false ",
            "CHO, HAN, false",
            "CHO, CHO, true"
    })
    void 기물이_특정_팀인지_판단한다(Team pieceTeam, Team team, boolean expected) {
        // given
        Piece piece = new Ma(pieceTeam);

        // when
        boolean actual = piece.isTeam(team);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
