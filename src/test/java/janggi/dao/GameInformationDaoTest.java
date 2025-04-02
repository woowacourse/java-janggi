package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.data.fixture.DBFixture;
import janggi.data.spy.TestDBConnector;
import janggi.db.DBConnector;
import janggi.game.GameInformation;
import janggi.rule.GameState;
import janggi.rule.PieceAssignType;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameInformationDaoTest {

    DBConnector dbConnector;
    GameInformationDao gameInformationDao;

    @BeforeEach
    void beforeEach() {
        dbConnector = new TestDBConnector();
        gameInformationDao = new GameInformationDao(dbConnector);
        DBFixture.resetTable(dbConnector, "games");
    }

    @AfterEach
    void afterEach() {
        DBFixture.resetTable(dbConnector, "games");
    }


    @DisplayName("새로운 게임정보를 삽입하고 조회할 수 있다.")
    @Test
    void canAddNew() {
        GameInformation gameInformation =
                gameInformationDao.addNew("newGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);

        GameInformation savedGameInformation = gameInformationDao.findById(gameInformation.getGameId());
        assertAll(
                () -> assertThat(savedGameInformation.getGameTitle()).isEqualTo("newGame"),
                () -> assertThat(savedGameInformation.getChoAssignType()).isEqualTo(PieceAssignType.IN_SANG),
                () -> assertThat(savedGameInformation.getHanAssignType()).isEqualTo(PieceAssignType.IN_SANG),
                () -> assertThat(savedGameInformation.getGameState()).isEqualTo(GameState.PLAY)
        );
    }

    @DisplayName("특정 게임을 종료처리할 수 있다.")
    @Test
    void canChangeGameStateToEnd() {
        GameInformation newGameInformation =
                gameInformationDao.addNew("endGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        gameInformationDao.updateGameStateToEnd(newGameInformation.getGameId());

        GameInformation savedGameInformation = gameInformationDao.findById(newGameInformation.getGameId());
        assertAll(
                () -> assertThat(savedGameInformation.getGameId()).isEqualTo(newGameInformation.getGameId()),
                () -> assertThat(savedGameInformation.getGameState()).isEqualTo(GameState.END)
        );
    }

    @DisplayName("진행 중인 게임정보들을 조회할 수 있다.")
    @Test
    void findAllInPlaying() {
        GameInformation endGame =
                gameInformationDao.addNew("endGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        gameInformationDao.updateGameStateToEnd(endGame.getGameId());
        GameInformation firstContinuedGame =
                gameInformationDao.addNew("continuedGame1", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        GameInformation secondContinuedGame =
                gameInformationDao.addNew("continuedGame2", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);

        List<GameInformation> continuedGames = gameInformationDao.findAllInPlaying();
        assertThat(continuedGames).containsExactly(firstContinuedGame, secondContinuedGame);
    }
}