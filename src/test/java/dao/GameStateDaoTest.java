package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static player.Nation.CHO;
import static player.Nation.HAN;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import player.Nation;

public class GameStateDaoTest {

    @Test
    @DisplayName("초나라부터 시작 테스트")
    public void setTurnTest() {
        GameStateDao gameStateDao = new GameStateDao();
        gameStateDao.getConnection();
        gameStateDao.insertGameState();

        Nation nation = gameStateDao.getCurrentTurn();

        assertThat(nation == CHO).isTrue();

        gameStateDao.deleteAllGameState();
    }

    @Test
    @DisplayName("턴 교대 테스트")
    void changeTurnTest() {
        GameStateDao gameStateDao = new GameStateDao();
        gameStateDao.getConnection();
        gameStateDao.insertGameState();

        gameStateDao.changeAttackNation(HAN);

        assertThat(gameStateDao.getCurrentTurn().equals(HAN)).isTrue();
        gameStateDao.deleteAllGameState();
    }

}
