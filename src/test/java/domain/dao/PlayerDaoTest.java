package domain.dao;

import static org.assertj.core.api.Assertions.*;

import domain.participants.Player;
import domain.participants.Players;
import domain.piece.TeamType;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerDaoTest {

    @Test
    @DisplayName("player 저장 기능 테스트")
    void savePlayerTest() {
        PlayerDao playerDao = new PlayerDao();
        Player player1 = new Player("a", TeamType.CHO);
        Player player2 = new Player("b", TeamType.HAN);
        playerDao.save(player1);
        playerDao.save(player2);
    }

    @Test
    @DisplayName("players 조회 기능 테스트")
    void findPlayers(){
        PlayerDao playerDao = new PlayerDao();
        Optional<Players> optionalPlayers = playerDao.findPlayers();
        assertThat(optionalPlayers.isPresent()).isTrue();
        Players players = optionalPlayers.get();
        assertThat(players.getChoPlayerName()).isEqualTo("a");
        assertThat(players.getHanPlayerName()).isEqualTo("b");
    }
}