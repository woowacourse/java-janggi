package dao;

import database.H2ConnectManager;
import domain.board.GameStatus;
import domain.piece.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameStatusDaoTest {

    private GameStatusDao gameStatusDao;

    @BeforeEach
    void setUp() {
        gameStatusDao = new GameStatusDao(new H2ConnectManager());
    }

    @Test
    void saveAndLoadTurn() {
        // given
        Country expectedCountry = Country.HAN;
        int expectedTurnCount = 5;

        // when
        gameStatusDao.saveTurn(expectedCountry, expectedTurnCount);
        GameStatus status = gameStatusDao.loadTurn();

        // then
        assertThat(status).isNotNull();
        assertThat(status.country()).isEqualTo(expectedCountry);
        assertThat(status.turnCount()).isEqualTo(expectedTurnCount);
    }

    @Test
    void overwriteTurn() {
        // given
        gameStatusDao.saveTurn(Country.HAN, 3);

        // when
        gameStatusDao.saveTurn(Country.CHO, 7);
        GameStatus status = gameStatusDao.loadTurn();

        // then
        assertThat(status).isNotNull();
        assertThat(status.country()).isEqualTo(Country.CHO);
        assertThat(status.turnCount()).isEqualTo(7);
    }
}
