package domain.player;

import domain.Team;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PlayersTest {

    @Test
    void 턴을_넘길_수_있다() {
        // given
        Players players1 = Players.ofNames("플레이어1", "플레이어2");
        Players players2 = Players.ofNames("플레이어1", "플레이어2");
        // when
        players1.nextTurn();
        // then
        Assertions.assertThat(players1).isNotEqualTo(players2);
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, true",
            "HAN, false"
    })
    void 이번_턴의_플레이어와_특정_기물이_같은_팀인지_판단한다(Team team, boolean expected) {
        // given
        Players players1 = Players.ofNames("플레이어1", "플레이어2");
        Piece piece1 = new Pawn(team);

        // when
        boolean actual = players1.isSameTeamThisTurnPlayerAndPiece(piece1);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}