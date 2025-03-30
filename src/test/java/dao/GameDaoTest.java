package dao;

import static org.assertj.core.api.Assertions.assertThat;

import model.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class GameDaoTest {

    private final TestDaoConfiguration testDaoConfiguration = new TestDaoConfiguration();
    private GameDao gameDao = new GameDao(testDaoConfiguration);

    @AfterEach
    void clearData() {
        gameDao.deleteGame();
    }

    @Test
    public void addTurn() {
        gameDao.addTurn(Team.GREEN);

    }

    @Test
    public void deleteTurn() {
        gameDao.deleteTurn();
    }

    @Test
    public void getTurn() {
        Team turn = gameDao.getTurn();
        assertThat(turn).isNotNull();
    }

    @Test
    public void updateTurn() {
        gameDao.updateTurn(Team.RED);
        assertThat(gameDao.getTurn()).isEqualTo(Team.RED);
    }
}
