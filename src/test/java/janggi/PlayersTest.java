package janggi;

import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayersTest {

    @Test
    @DisplayName("팀으로 플레이어를 찾을 수 있다")
    void create() {
        // given
        Players players = Players.create();

        // when
        Player choPlayer = players.getPlayer(Team.CHO);
        Player hanPlayer = players.getPlayer(Team.HAN);

        // then
        assertThat(choPlayer.getTeam()).isEqualTo(Team.CHO);
        assertThat(hanPlayer.getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("두 플레이어의 피스를 가져올 수 있다")
    void getBothPiece() {
        // given
        Players players = Players.create();

        // when
        Pieces bothPieces = players.getBothPieces();

        // then
        assertThat(bothPieces.getPieces().size()).isEqualTo(32);
    }

    @Test
    @DisplayName("팀으로 플레이어의 점수를 가져올 수 있다")
    void getScore() {
        // given
        Players players = Players.create();
        Player cho = players.getPlayer(Team.CHO);
        cho.addScore(new Score(11111));
        Player han = players.getPlayer(Team.HAN);
        han.addScore(new Score(22222));

        // when
        Score choScore = players.getScore(Team.CHO);
        Score hanScore = players.getScore(Team.HAN);

        // then
        assertThat(choScore).isEqualTo(new Score(11111));
        assertThat(hanScore).isEqualTo(new Score(22222));
    }

}
