package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.TeamType;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.H2ConnectionFactory;

class JanggiGameDaoTest {

    private JanggiGameDao janggiGameDao;
    private Connection connection;

    @BeforeEach
    void setup() throws SQLException {
        H2ConnectionFactory h2Connection = new H2ConnectionFactory();
        h2Connection.initializeTable();
        connection = h2Connection.getConnection();
        connection.setAutoCommit(false);
        janggiGameDao = new JanggiGameDao();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("장기 게임의 정보를 저장한다")
    void saveJanggiGameTest() {
        // given
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;

        // when & then
        assertThatCode(() -> janggiGameDao.saveJanggiGame(turnState, gameState, connection))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("진행중인 게임의 정보들을 반환한다")
    void findInProgressGamesTest() {
        // given
        int savedCount = janggiGameDao.findInProgressGameIds(connection).size();
        int newGameCount = 3;
        for (int i = 0; i < newGameCount; i++) {
            saveNewGame();
        }

        // when
        List<Long> inProgressGames = janggiGameDao.findInProgressGameIds(connection);

        // then
        assertThat(inProgressGames).hasSize(savedCount + 3);
    }

    @Test
    @DisplayName("게임의 턴 상태를 반환한다")
    void findTurnStateByIdTest() {
        // given
        long gameId = saveNewGame();

        // when
        TurnState turnState = janggiGameDao.findTurnStateById(gameId, connection).get();

        // then
        assertAll(
                () -> assertThat(turnState.playerTeam()).isEqualTo(TeamType.CHO),
                () -> assertThat(turnState.undoLast()).isFalse()
        );
    }

    @Test
    @DisplayName("게임 진행 상태를 변경한다")
    void updateGameStateTest() {
        // given
        long gameId = saveNewGame();

        // when
        GameState gameState = GameState.FINISHED_SCORE;
        int resultRowCount = janggiGameDao.updateGameState(gameId, gameState, connection);

        // then
        assertThat(resultRowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("게임 턴 상황을 변경한다")
    void updateTurnStateTest() {
        // given
        long gameId = saveNewGame();

        // when
        TurnState turnState = new TurnState(true, TeamType.HAN);
        int resultRowCount = janggiGameDao.updateTurnState(gameId, turnState, connection);

        // then
        assertThat(resultRowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("게임의 종료 상황을 반환한다")
    void findGameStateByIdTest() {
        // given
        long gameId = saveNewGame();

        // when
        GameState actual = janggiGameDao.findGameStateById(gameId, connection).get();

        // then
        GameState expected = GameState.IN_PROGRESS;
        assertThat(actual).isEqualTo(expected);
    }

    private long saveNewGame() {
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;

        return janggiGameDao.saveJanggiGame(turnState, gameState, connection);
    }
}
