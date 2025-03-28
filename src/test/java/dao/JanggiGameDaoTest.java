package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.TeamType;
import domain.game.dto.JanggiGameResponseDto;
import domain.player.Players;
import domain.player.Usernames;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DBConnectionUtil;

class JanggiGameDaoTest {

    private JanggiGameDao janggiGameDao;
    private Connection connection;

    @BeforeEach
    void setup() throws SQLException {
        connection = DBConnectionUtil.getConnection();
        connection.setAutoCommit(false);
        janggiGameDao = new JanggiGameDao(connection);
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
        String choPlayerName = "루키";
        String hanPlayerName = "코기";
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;

        // when & then
        assertThatCode(() -> janggiGameDao.saveJanggiGame(players, turnState, gameState))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("진행중인 게임의 정보들을 반환한다")
    void findInProgressGamesTest() {
        // given
        int gameCount = 3;
        for (int i = 0; i < gameCount; i++) {
            saveNewGame();
        }

        // when
        List<JanggiGameResponseDto> inProgressGames = janggiGameDao.findInProgressGames();

        // then
        assertThat(inProgressGames).hasSize(3);
    }

    @Test
    @DisplayName("진행중인 게임의 정보를 반환한다")
    void findInProgressGameTest() {
        // given
        String choPlayerName = "피케이";
        String hanPlayerName = "PK";
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);
        TurnState turnState = new TurnState(true, TeamType.HAN);
        GameState gameState = GameState.IN_PROGRESS;
        janggiGameDao.saveJanggiGame(players, turnState, gameState);

        // when
        List<JanggiGameResponseDto> inProgressGames = janggiGameDao.findInProgressGames();
        JanggiGameResponseDto inProgressGame = inProgressGames.getFirst();

        // then
        assertAll(
                () -> assertThat(inProgressGame.hanPlayerName()).isEqualTo("PK"),
                () -> assertThat(inProgressGame.choPlayerName()).isEqualTo("피케이")
        );
    }

    @Test
    @DisplayName("게임의 턴 상태를 반환한다")
    void findTurnStateByIdTest() {
        // given
        long gameId = saveNewGame();

        // when
        TurnState turnState = janggiGameDao.findTurnStateById(gameId).get();

        // then
        assertAll(
                () -> assertThat(turnState.playerTeam()).isEqualTo(TeamType.CHO),
                () -> assertThat(turnState.undoLast()).isFalse()
        );
    }

    private long saveNewGame() {
        String choPlayerName = "루키";
        String hanPlayerName = "코기";
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);
        TurnState turnState = new TurnState(false, TeamType.CHO);
        GameState gameState = GameState.IN_PROGRESS;

        return janggiGameDao.saveJanggiGame(players, turnState, gameState);
    }
}
