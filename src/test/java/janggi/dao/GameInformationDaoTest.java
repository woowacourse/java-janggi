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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameInformationDaoTest {

    DBConnector dbConnector = new TestDBConnector();
    GameInformationDao gameInformationDao = new GameInformationDao(dbConnector);

    @AfterEach
    void afterEach() {
        DBFixture.resetTable(dbConnector, "games");
    }

    @DisplayName("새로운 게임정보를 삽입하고 조회할 수 있다.")
    @Test
    void canAddNew() {
        GameInformation gameInformation =
                gameInformationDao.addNew("newGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);

        GameInformation savedGameInformation = gameInformationDao.findById(gameInformation.gameId());
        assertAll(
                () -> assertThat(savedGameInformation.gameTitle()).isEqualTo("newGame"),
                () -> assertThat(savedGameInformation.choAssignType()).isEqualTo(PieceAssignType.IN_SANG),
                () -> assertThat(savedGameInformation.hanAssignType()).isEqualTo(PieceAssignType.IN_SANG),
                () -> assertThat(savedGameInformation.gameState()).isEqualTo(GameState.PLAY)
        );
    }

    @DisplayName("특정 게임을 종료처리할 수 있다.")
    @Test
    void canChangeGameStateToEnd() {
        GameInformation newGameInformation =
                gameInformationDao.addNew("endGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        gameInformationDao.updateGameStateToEnd(newGameInformation.gameId());

        GameInformation savedGameInformation = gameInformationDao.findById(newGameInformation.gameId());
        assertAll(
                () -> assertThat(savedGameInformation.gameId()).isEqualTo(newGameInformation.gameId()),
                () -> assertThat(savedGameInformation.gameState()).isEqualTo(GameState.END)
        );
    }

    @DisplayName("진행 중인 게임정보들을 조회할 수 있다.")
    @Test
    void findAllInPlaying() {
        GameInformation endGame =
                gameInformationDao.addNew("endGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        gameInformationDao.updateGameStateToEnd(endGame.gameId());
        GameInformation firstContinuedGame =
                gameInformationDao.addNew("continuedGame1", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        GameInformation secondContinuedGame =
                gameInformationDao.addNew("continuedGame2", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);

        List<GameInformation> continuedGames = gameInformationDao.findAllInPlaying();
        assertThat(continuedGames).containsExactly(firstContinuedGame, secondContinuedGame);
    }
}