package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.entity.GameRoomEntity;
import janggi.infra.entity.PiecePositionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.*;
import java.util.List;

import static janggi.fixture.TestFixture.createGameRoomEntity;
import static janggi.fixture.TestFixture.createPiecePositionEntity;
import static org.assertj.core.api.Assertions.assertThat;

class JdbcBoardDAOTest {

    private final DataSource dataSource = new TestDataSourceConfig().dataSource();
    private final JdbcBoardDAO jdbcBoardDAO = new JdbcBoardDAO(dataSource);

    private static final String SAVE_SQL = "INSERT INTO game_room(room_name, last_turn, last_played_at) VALUES(?, ?, ?)";

    @Test
    @DisplayName("기물 위치목록을 저장한다.")
    public void saveAll_success() throws Exception {
        // given
        GameRoomEntity gameRoomEntity = saveGameRoomEntity();

        List<PiecePositionEntity> positionEntities = List.of(
                createPiecePositionEntity(Position.from(1, 1), PieceType.CHARIOT, Dynasty.CHO, gameRoomEntity),
                createPiecePositionEntity(Position.from(2, 5), PieceType.GENERAL, Dynasty.CHO, gameRoomEntity),
                createPiecePositionEntity(Position.from(9, 5), PieceType.GENERAL, Dynasty.HAN, gameRoomEntity)
        );

        // when
        jdbcBoardDAO.saveAll(positionEntities);

        // then
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM piece_position");
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertThat(resultSet.getInt(1)).isEqualTo(3);
        }
    }

    private GameRoomEntity saveGameRoomEntity() throws SQLException {
        GameRoomEntity gameRoomEntity = createGameRoomEntity();
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        SAVE_SQL,
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {
            pstmt.setString(1, gameRoomEntity.roomName());
            pstmt.setString(2, gameRoomEntity.lastTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameRoomEntity.lastPlayedAt()));
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    return new GameRoomEntity(
                            generatedId,
                            gameRoomEntity.roomName(),
                            gameRoomEntity.lastTurn(),
                            gameRoomEntity.lastPlayedAt()
                    );
                }
            }
        }

        throw new SQLException("생성된 game_room id를 가져오지 못했습니다.");
    }


}
