package janggi.application;

import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.game.Game;
import janggi.domain.game.RoomName;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.dao.JdbcGameDAO;
import janggi.infra.dao.JdbcPiecePositionDAO;
import janggi.infra.util.ConnectionProvider;
import janggi.infra.transaction.TransactionTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static janggi.domain.dynasty.Dynasty.*;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.fixture.TestFixture.saveGameRoomEntity;
import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final DataSource dataSource = testDataSourceConfig.dataSource();
    private final GameService gameService = new GameService(
            new JdbcGameDAO(new ConnectionProvider(dataSource)),
            new JdbcPiecePositionDAO(new ConnectionProvider(dataSource)),
            new TransactionTemplate(dataSource)
    );

    @AfterEach
    void tearDown() {
        try (
                Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate("DELETE FROM piece_position");
            statement.executeUpdate("DELETE FROM game");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("새로운 게임을 만든다.")
    public void createGame_success() {
        // given
        DefaultBoardDesignPolicy boardDesignPolicy = new DefaultBoardDesignPolicy(Map.of(
                CHO, HorseElephantPosition.EHEH,
                HAN, HorseElephantPosition.EHEH
        ));
        String roomName = "room";
        LocalDateTime lastPlayedAt = LocalDateTime.of(2026, 10, 7, 10, 0);

        // when
        Game game = gameService.createGame(boardDesignPolicy, roomName, lastPlayedAt);

        // then
        assertThat(game).isNotNull();
        assertThat(game.roomName()).isEqualTo(roomName);
        assertThat(game.lastPlayedAt()).isEqualTo(lastPlayedAt);
    }

    @Test
    @DisplayName("게임 목록을 가져온다.")
    public void getRecentlyPlayedGames() throws Exception {
        // given
        saveGameRoomEntity(new RoomName("room1"), CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);
        saveGameRoomEntity(new RoomName("room2"), HAN,
                LocalDateTime.of(2025, 4, 5, 10, 0), dataSource);
        saveGameRoomEntity(new RoomName("room3"), CHO,
                LocalDateTime.of(2026, 4, 5, 10, 0), dataSource);

        // when
        List<GameDto> gameList = gameService.getRecentlyPlayedGames();

        // then
        assertThat(gameList).hasSize(3)
                .extracting(GameDto::roomName)
                .containsExactly("room3", "room2", "room1");
        assertThat(gameList)
                .extracting(GameDto::id)
                .allMatch(Objects::nonNull);
    }

}
