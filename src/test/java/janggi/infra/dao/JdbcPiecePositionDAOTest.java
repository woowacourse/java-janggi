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
import java.time.LocalDateTime;
import java.util.List;

import static janggi.fixture.TestFixture.createPiecePositionEntity;
import static janggi.fixture.TestFixture.saveGameRoomEntity;
import static org.assertj.core.api.Assertions.assertThat;

class JdbcPiecePositionDAOTest {

    private final DataSource dataSource = new TestDataSourceConfig().dataSource();
    private final JdbcPiecePositionDAO jdbcBoardDAO = new JdbcPiecePositionDAO(dataSource);

    @Test
    @DisplayName("기물 위치목록을 저장한다.")
    public void saveAll_success() throws Exception {
        // given
        GameRoomEntity gameRoomEntity = saveGameRoomEntity(
                "room1",
                Dynasty.CHO,
                LocalDateTime.of(2026, 4, 3, 15, 30),
                dataSource
        );

        List<PiecePositionEntity> positionEntities = List.of(
                createPiecePositionEntity(Position.from(1, 1), PieceType.CHARIOT, Dynasty.CHO, gameRoomEntity),
                createPiecePositionEntity(Position.from(2, 5), PieceType.GENERAL, Dynasty.CHO, gameRoomEntity),
                createPiecePositionEntity(Position.from(9, 5), PieceType.GENERAL, Dynasty.HAN, gameRoomEntity)
        );

        // when
        List<Long> generatedKeys = jdbcBoardDAO.saveAll(positionEntities);

        // then
        try (
                Connection conn = dataSource.getConnection();
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


}
