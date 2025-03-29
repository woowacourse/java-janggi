package dao;

import static org.assertj.core.api.Assertions.assertThat;

import model.Team;
import org.junit.jupiter.api.Test;

public class GameDaoTest {

    private final DaoConfiguration daoConfiguration = new DaoConfiguration();
    private GameDao gameDao = new GameDao(daoConfiguration);

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
