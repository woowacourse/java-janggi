package janggi.application;

import janggi.application.dto.GameDto;
import janggi.application.dto.GameRoomDto;
import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.game.RoomName;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.dao.JdbcGameDAO;
import janggi.infra.dao.JdbcPiecePositionDAO;
import janggi.infra.entity.GameEntity;
import janggi.infra.util.ConnectionProvider;
import janggi.infra.transaction.TransactionTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static janggi.domain.dynasty.Dynasty.*;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.fixture.TestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final DataSource dataSource = testDataSourceConfig.dataSource();
    private final TransactionTemplate transactionTemplate = new TransactionTemplate(dataSource);
    private final GameService gameService = new GameService(
            new JdbcGameDAO(new ConnectionProvider(dataSource)),
            new JdbcPiecePositionDAO(new ConnectionProvider(dataSource)),
            transactionTemplate
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
        GameDto gameDto = gameService.createGame(boardDesignPolicy, roomName, lastPlayedAt);

        // then
        Game game = gameDto.game();
        assertThat(game).isNotNull();
        assertThat(game.roomName().roomName()).isEqualTo(roomName);
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
        List<GameRoomDto> gameList = gameService.getRecentlyPlayedGames();

        // then
        assertThat(gameList).hasSize(3)
                .extracting(GameRoomDto::roomName)
                .containsExactly("room3", "room2", "room1");
        assertThat(gameList)
                .extracting(GameRoomDto::id)
                .allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("특정 ID의 게임을 가져온다.")
    public void loadGame_success() throws Exception {
        // given
        GameEntity gameEntity = saveGameRoomEntity(new RoomName("room1"), CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);

        Long gameId = gameEntity.id();
        savePiecePositionEntity(Position.from(1, 1), CHARIOT, CHO, gameId, dataSource);
        savePiecePositionEntity(Position.from(2, 5), GENERAL, CHO, gameId, dataSource);
        savePiecePositionEntity(Position.from(9, 5), GENERAL, HAN, gameId, dataSource);

        // when
        GameDto gameDto = gameService.loadGame(gameEntity.id());

        // then
        Game game = gameDto.game();
        assertThat(game).extracting(
                Game::roomName, Game::currentTurn, Game::lastPlayedAt
        ).contains(
                new RoomName("room1"), CHO, LocalDateTime.of(2024, 4, 5, 10, 0));
        assertThat(game.boardMap())
                .containsExactlyInAnyOrderEntriesOf(
                        Map.of(
                                Position.from(1, 1), new Piece(CHO, CHARIOT),
                                Position.from(2, 5), new Piece(CHO, GENERAL),
                                Position.from(9, 5), new Piece(HAN, GENERAL)
                        )
                );
    }

    @Test
    @DisplayName("특정 게임에서 특정 위치에 있는 기물을 다른 위치로 이동시킨다.")
    public void movePiece_success() throws Exception {
        // given
        Dynasty currentTurn = CHO;
        GameEntity gameEntity = saveGameRoomEntity(new RoomName("room1"), currentTurn,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);

        int fromRow = 1;
        int fromColumn = 1;
        Position from = Position.from(fromRow, fromColumn);
        savePiecePositionEntity(from, CHARIOT, CHO, gameEntity.id(), dataSource);

        int toRow = 1;
        int toColumn = 2;
        Position to = Position.from(toRow, toColumn);
        LocalDateTime updatedLastPlayedAt = LocalDateTime.of(2024, 4, 5, 17, 12);

        // when
        GameDto gameDto = gameService.movePiece(gameEntity.id(), from, to, updatedLastPlayedAt);

        // then
        Game game = gameDto.game();
        assertThat(game.currentTurn())
                .isEqualTo(currentTurn.next());
        assertThat(game.lastPlayedAt())
                .isEqualTo(updatedLastPlayedAt);
        assertThat(game.boardMap())
                .doesNotContainKey(from)
                .containsKey(to);
        assertDatabase(gameEntity, currentTurn, updatedLastPlayedAt, fromRow, fromColumn, toRow, toColumn);
    }

    private void assertDatabase(GameEntity gameEntity, Dynasty currentTurn, LocalDateTime updatedLastPlayedAt, int fromRow, int fromColumn, int toRow, int toColumn) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement gameStatement = conn.prepareStatement("SELECT * FROM game WHERE game_id = ?");
                PreparedStatement piecePositionStatement = conn.prepareStatement(
                        "SELECT COUNT(*) FROM piece_position WHERE game_id = ? AND piece_row = ? AND  piece_column = ?");
        ) {
            assertGameUpdate(gameStatement, gameEntity, currentTurn, updatedLastPlayedAt);
            assertPiecePositionUpdate(piecePositionStatement, gameEntity, fromRow, fromColumn, toRow, toColumn);
        }
    }

    private static void assertGameUpdate(PreparedStatement gameStatement, GameEntity gameEntity, Dynasty currentTurn, LocalDateTime updatedLastPlayedAt) throws SQLException {
        gameStatement.setLong(1, gameEntity.id());
        ResultSet gameResultSet = gameStatement.executeQuery();
        gameResultSet.next();
        assertThat(gameResultSet.getString("current_turn")).isEqualTo(currentTurn.next().name());
        assertThat(gameResultSet.getTimestamp("last_played_at")).isEqualTo(Timestamp.valueOf(updatedLastPlayedAt));
    }

    private static void assertPiecePositionUpdate(
            PreparedStatement piecePositionStatement, GameEntity gameEntity,
            int fromRow, int fromColumn,
            int toRow, int toColumn) throws SQLException {
        piecePositionStatement.setLong(1, gameEntity.id());
        piecePositionStatement.setInt(2, fromRow);
        piecePositionStatement.setInt(3, fromColumn);
        ResultSet fromResultSet = piecePositionStatement.executeQuery();
        fromResultSet.next();
        assertThat(fromResultSet.getInt(1)).isEqualTo(0);

        piecePositionStatement.setLong(1, gameEntity.id());
        piecePositionStatement.setInt(2, toRow);
        piecePositionStatement.setInt(3, toColumn);
        ResultSet toResultSet = piecePositionStatement.executeQuery();
        toResultSet.next();
        assertThat(toResultSet.getInt(1)).isEqualTo(1);
    }

}
