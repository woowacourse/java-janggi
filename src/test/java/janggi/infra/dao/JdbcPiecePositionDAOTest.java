package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.entity.GameEntity;
import janggi.infra.entity.PiecePositionEntity;
import janggi.infra.util.ConnectionProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.fixture.TestFixture.*;
import static janggi.fixture.TestFixture.savePiecePositionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class JdbcPiecePositionDAOTest {

    private final DataSource dataSource = new TestDataSourceConfig().dataSource();
    private final ConnectionProvider connectionProvider = new ConnectionProvider(dataSource);
    private final JdbcPiecePositionDAO jdbcPiecePositionDAO = new JdbcPiecePositionDAO(connectionProvider);


    @AfterEach
    void tearDown() {
        try (
                Connection conn = connectionProvider.getConnection();
                Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate("DELETE FROM piece_position");
            statement.executeUpdate("DELETE FROM game");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("기물 위치목록을 저장한다.")
    public void saveAll_success() throws Exception {
        // given
        GameEntity gameEntity = saveGameRoomEntity(
                new RoomName("room1"),
                Dynasty.CHO,
                LocalDateTime.of(2026, 4, 3, 15, 30),
                dataSource
        );

        Long gameId = gameEntity.id();
        List<PiecePositionEntity> positionEntities = List.of(
                createPiecePositionEntity(Position.from(1, 1), PieceType.CHARIOT, Dynasty.CHO, gameId),
                createPiecePositionEntity(Position.from(2, 5), PieceType.GENERAL, Dynasty.CHO, gameId),
                createPiecePositionEntity(Position.from(9, 5), PieceType.GENERAL, Dynasty.HAN, gameId)
        );

        // when
        List<Long> generatedKeys = jdbcPiecePositionDAO.saveAll(positionEntities);

        // then
        try (
                Connection conn = connectionProvider.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(createFindByGeneratedKeysQuery(generatedKeys));
        ) {
            bindGeneratedKeysParameter(generatedKeys, preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertThat(resultSet.getInt(1)).isEqualTo(3);
        }
    }

    private static String createFindByGeneratedKeysQuery(List<Long> generatedKeys) {
        String query = "SELECT COUNT(*) FROM piece_position WHERE piece_position_id IN ";
        StringBuilder sb = new StringBuilder(query);
        sb.append("(");
        sb.append("?,".repeat(generatedKeys.size()));
        sb.delete(sb.length() - 1, sb.length());
        sb.append(")");
        return sb.toString();
    }

    private static void bindGeneratedKeysParameter(List<Long> generatedKeys, PreparedStatement preparedStatement) throws SQLException {
        int idx = 1;
        for (Long generatedKey : generatedKeys) {
            preparedStatement.setLong(idx++, generatedKey);
        }
    }

    @Test
    @DisplayName("특정 게임 id의 기물들을 가져온다.")
    public void findAllPiecesByGameId_success() throws Exception {
        // given
        GameEntity gameEntity = saveGameRoomEntity(new RoomName("room1"), CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);

        Long gameId = gameEntity.id();
        savePiecePositionEntity(Position.from(1, 1), CHARIOT, CHO, gameId, dataSource);
        savePiecePositionEntity(Position.from(2, 5), GENERAL, CHO, gameId, dataSource);
        savePiecePositionEntity(Position.from(9, 5), GENERAL, HAN, gameId, dataSource);

        // when
        List<PiecePositionEntity> pieces = jdbcPiecePositionDAO.findAllPiecesByGameId(gameEntity.id());

        // then
        assertThat(pieces)
                .hasSize(3)
                .extracting(
                        PiecePositionEntity::position, PiecePositionEntity::pieceType, PiecePositionEntity::dynasty
                )
                .contains(
                        tuple(Position.from(1, 1), CHARIOT, CHO),
                        tuple(Position.from(2, 5), GENERAL, CHO),
                        tuple(Position.from(9, 5), GENERAL, HAN)
                );
    }

    @Test
    @DisplayName("특정 위치에 있는 piece를 삭제한다.")
    public void deleteByGameIdAndPosition_success() throws Exception {
        // given
        GameEntity gameEntity = saveGameRoomEntity(
                new RoomName("room1"),
                CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0),
                dataSource
        );

        Position position = Position.from(1, 1);
        savePiecePositionEntity(position, CHARIOT, CHO, gameEntity.id(), dataSource);

        // when
        jdbcPiecePositionDAO.deleteByGameIdAndPosition(gameEntity.id(), position);

        // then
        try (
                Connection conn = connectionProvider.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "SELECT COUNT(*) FROM piece_position WHERE game_id = ? AND piece_row = ? AND piece_column = ?")
        ) {
            preparedStatement.setLong(1, gameEntity.id());
            preparedStatement.setInt(2, position.row().row());
            preparedStatement.setInt(3, position.column().column());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertThat(resultSet.getInt(1)).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("PiecePosition의 piece_row와 piece_column을 수정한다.")
    public void updatePosition_success() throws Exception {
        // given
        GameEntity gameEntity = saveGameRoomEntity(new RoomName("room1"), CHO,
                LocalDateTime.of(2024, 4, 5, 10, 0), dataSource);

        int fromRow = 1;
        int fromColumn = 1;
        Position from = Position.from(fromRow, fromColumn);
        savePiecePositionEntity(from, CHARIOT, CHO, gameEntity.id(), dataSource);

        int toRow = 1;
        int toColumn = 2;
        Position to = Position.from(toRow, toColumn);

        // when
        jdbcPiecePositionDAO.updatePosition(gameEntity.id(), from, to);

        // then
        try (
                Connection conn = connectionProvider.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "SELECT COUNT(*) FROM piece_position WHERE game_id = ? AND piece_row = ? AND  piece_column = ?")
        ) {
            preparedStatement.setLong(1, gameEntity.id());
            preparedStatement.setInt(2, fromRow);
            preparedStatement.setInt(3, fromColumn);
            ResultSet fromResultSet = preparedStatement.executeQuery();
            fromResultSet.next();

            assertThat(fromResultSet.getInt(1)).isEqualTo(0);


            preparedStatement.setLong(1, gameEntity.id());
            preparedStatement.setInt(2, toRow);
            preparedStatement.setInt(3, toColumn);
            ResultSet toResultSet = preparedStatement.executeQuery();
            toResultSet.next();

            assertThat(toResultSet.getInt(1)).isEqualTo(1);
        }
    }


}
