package domain.player;

import static domain.TestUtil.createPlayer;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import common.JanggiException;
import org.junit.jupiter.api.Test;

class PlayersTest {
    private static final String PLAYER_DUPLICATED = "플레이어는 중복될 수 없습니다.";
    private static final String PLAYER_LIMIT_EXCEEDED = "플레이어는 두 명을 초과할 수 없습니다.";

    @Test
    void 닉네임이_중복되면_에러를_던진다() {
        Player player1 = createPlayer("봉구스", Team.CHO);
        Player player2 = createPlayer("봉구스", Team.HAN);

        Players players = new Players();
        players = players.add(player1);
        Players currentPlayers = players;

        assertThatThrownBy(() -> currentPlayers.add(player2))
                .isInstanceOf(JanggiException.class)
                .hasMessageContaining(PLAYER_DUPLICATED);
    }

    @Test
    void 플레이어가_세_명이면_에러를_던진다() {
        Player player1 = createPlayer("봉구스1", Team.CHO);
        Player player2 = createPlayer("봉구스2", Team.HAN);
        Player player3 = createPlayer("봉구스3", Team.HAN);

        Players players = new Players();
        players = players.add(player1);
        players = players.add(player2);
        Players currentPlayers = players;

        assertThatThrownBy(() -> currentPlayers.add(player3))
                .isInstanceOf(JanggiException.class)
                .hasMessageContaining(PLAYER_LIMIT_EXCEEDED);
    }
}
