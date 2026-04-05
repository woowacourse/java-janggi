package dao;

import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import domain.game.GameStatus;
import infra.db.DbBootstrap;
import infra.db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRoomTest {
    private final GameRoom gameRoom = new GameRoom();

    @BeforeEach
    void setUp() {
        DbBootstrap.initializeForTest();
    }

    @Test
    void 게임을_생성하면_DB에_초기_턴과_상태가_저장된다() throws SQLException {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status FROM game WHERE game_id = ?")) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("current_turn")).isEqualTo("CHO");
                assertThat(resultSet.getString("status")).isEqualTo("PROGRESS");
            }
        }
    }

    @Test
    void 게임_상태를_업데이트하면_턴과_상태가_변경된다() throws SQLException {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");

        gameRoom.updateGameState(gameId, HAN, GameStatus.HAN_WIN);

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status FROM game WHERE game_id = ?")) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("current_turn")).isEqualTo("HAN");
                assertThat(resultSet.getString("status")).isEqualTo("HAN_WIN");
            }
        }
    }

    @Test
    void findProgressGame_진행중인_게임이_있으면_ID를_반환한다() {
        long gameId1 = gameRoom.createGame("CHO Player", "HAN Player");
        long gameId2 = gameRoom.createGame("CHO Player2", "HAN Player2");

        Optional<Long> foundId = gameRoom.findProgressGame();

        assertThat(foundId).isPresent();
        assertThat(foundId.get()).isEqualTo(gameId2);
    }

    @Test
    void findProgressGame_진행중인_게임이_없으면_비어있음을_반환한다() {
        Optional<Long> existingGame = gameRoom.findProgressGame();
        while (existingGame.isPresent()) {
            gameRoom.updateGameState(existingGame.get(), HAN, GameStatus.CHO_WIN);
            existingGame = gameRoom.findProgressGame();
        }

        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        gameRoom.updateGameState(gameId, HAN, GameStatus.HAN_WIN);

        Optional<Long> foundId = gameRoom.findProgressGame();

        assertThat(foundId).isEmpty();
    }

    @Test
    void getCurrentTurn_해당_게임의_현재_차례를_반환한다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");

        var currentTurn = gameRoom.getCurrentTurn(gameId);

        assertThat(currentTurn.name()).isEqualTo("CHO");
    }

    @Test
    void getCurrentTurn_업데이트_후_변경된_차례를_반환한다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        gameRoom.updateGameState(gameId, HAN, GameStatus.PROGRESS);

        var currentTurn = gameRoom.getCurrentTurn(gameId);

        assertThat(currentTurn.name()).isEqualTo("HAN");
    }
}
