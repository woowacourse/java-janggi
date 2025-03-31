package database.repository.game;

import static org.assertj.core.api.Assertions.assertThat;

import database.connector.MySQLTestConnector;
import object.team.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private GameRepository gameRepository;

    @BeforeEach
    void beforeEach() {
        MySQLTestConnector connector = new MySQLTestConnector();
        gameRepository = new GameRepository(connector);
        gameRepository.initializeTable();
    }

    @Test
    @DisplayName("게임을 저장하고 불러온다.")
    void test1() {
        // given
        Country turnTeam = Country.CHO;
        boolean isEnd = true;
        int scoreHan = 12;
        int scoreCho = 7;

        // when
        gameRepository.saveGame(turnTeam, isEnd, scoreHan, scoreCho);
        GameDto loadedGame = gameRepository.loadGame();

        // then
        assertThat(loadedGame.turnTeam()).isEqualTo(turnTeam);
        assertThat(loadedGame.isEnd()).isEqualTo(isEnd);
        assertThat(loadedGame.scoreHan()).isEqualTo(scoreHan);
        assertThat(loadedGame.scoreCho()).isEqualTo(scoreCho);
    }
}
