package domain;

import domain.piece.Ma;
import domain.piece.Piece;
import domain.player.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PlayerTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "HAN, CHO, false",
            "CHO, CHO, true",
            "CHO, HAN, false"
    })
    void 플레이어와_장기말의_팀을_비교한다(Team playerTeam, Team pieceTeam, boolean expected) {
        // given
        Piece piece = new Ma(pieceTeam);
        Player player = new Player("짱구", playerTeam);

        // when
        boolean actual = player.isTeam(piece);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}