package dao;

import static org.assertj.core.api.Assertions.assertThat;

import model.piece.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameDaoTest {

    private final TestDaoConfiguration testDaoConfiguration = new TestDaoConfiguration();
    private GameDao gameDao = new GameDao(testDaoConfiguration);

    @AfterEach
    void clearData() {
        gameDao.deleteGame();
    }

    @DisplayName("가지고 있는 턴을 가져올 수 있어야 한다.")
    @Test
    void getTurn() {
        gameDao.addTurn(Team.RED);
        assertThat(gameDao.getTurn()).isEqualTo(Team.RED);
    }

    @DisplayName("턴이 없을 경우, GREEN 팀으로 세팅 되어야 한다.")
    @Test
    void when_not_exist_turn_then_setting_green() {
        gameDao.addTurn(Team.GREEN);
        assertThat(gameDao.getTurn()).isEqualTo(Team.GREEN);
    }

    @DisplayName("가지고 있는 Turn을 삭제한다.")
    @Test
    void deleteTurn() {
        gameDao.addTurn(Team.GREEN);
        gameDao.deleteTurn();
        assertThat(gameDao.getTurn()).isEqualTo(null);
    }

    @DisplayName("레드팀일 경우, 그린 팀으로 턴을 바꿀 수 있어야 한다.")
    @Test
    void when_current_red_then_change_green() {
        gameDao.addTurn(Team.RED);
        gameDao.updateTurn(Team.GREEN);
        assertThat(gameDao.getTurn()).isEqualTo(Team.GREEN);
    }

    @DisplayName("그린팀일 경우, 레드팀으로 턴을 바꿀 수 있어야 한다.")
    @Test
    void when_current_green_then_change_red() {
        gameDao.addTurn(Team.GREEN);
        gameDao.updateTurn(Team.RED);
        assertThat(gameDao.getTurn()).isEqualTo(Team.RED);
    }
}
