package dao;

import static org.assertj.core.api.Assertions.assertThat;

import model.Team;
import org.junit.jupiter.api.Test;

public class GameDaoTest {

    @Test
    public void addTurn() {
        GameDao gameDao = new GameDao();
        gameDao.addTurn(Team.GREEN);
    }

    @Test
    public void deleteTurn() {
        GameDao gameDao = new GameDao();
        gameDao.deleteTurn();
    }

    @Test
    public void getTurn() {
        GameDao gameDao = new GameDao();
        Team turn = gameDao.getTurn();
        assertThat(turn).isNotNull();
    }

    @Test
    public void updateTurn() {
        GameDao gameDao = new GameDao();
        gameDao.updateTurn(Team.RED);
        assertThat(gameDao.getTurn()).isEqualTo(Team.RED);
    }
}
