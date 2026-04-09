package janggi.repository.movement;

import static janggi.config.DatabaseManager.withTransaction;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.config.DatabaseManager;
import janggi.config.DdlAuto;
import janggi.config.TestConfig;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcMovementRepositoryTest {

    private final MovementRepository movementRepository = new JdbcMovementRepository();

    @BeforeAll
    static void setUp() {
        TestConfig.setUp();
    }

    @BeforeEach
    void setUpDatabase() {
        DatabaseManager.initTable(DdlAuto.CREATE_DROP);
    }

    @Test
    void 잡은_기물이_없으면_null로_이동_기록을_저장한다() throws Exception {
        // given
        Long gameId = TestConfig.insertGame("CHO", "PLAYING");

        // when
        withTransaction(connection -> {
                    movementRepository.save(
                            connection,
                            gameId,
                            Position.from(1, 1),
                            Position.from(2, 1),
                            null
                    );
                    return null;
                }
        );

        // then
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                             SELECT 
                                 janggi_game_id,
                                 src_row_pos,
                                 src_col_pos,
                                 dest_row_pos,
                                 dest_col_pos,
                                 dest_team,
                                 dest_type
                             FROM movement
                             """
             );
             ResultSet resultSet = statement.executeQuery()) {

            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getLong("janggi_game_id")).isEqualTo(gameId);
            assertThat(resultSet.getInt("src_row_pos")).isEqualTo(1);
            assertThat(resultSet.getInt("src_col_pos")).isEqualTo(1);
            assertThat(resultSet.getInt("dest_row_pos")).isEqualTo(2);
            assertThat(resultSet.getInt("dest_col_pos")).isEqualTo(1);
            assertThat(resultSet.getString("dest_team")).isNull();
            assertThat(resultSet.getString("dest_type")).isNull();
        }
    }

    @Test
    void 잡은_기물의_팀과_타입을_함께_저장한다() throws Exception {
        // given
        Long gameId = TestConfig.insertGame("CHO", "PLAYING");
        PieceEntity capturedPiece = PieceEntity.toEntity(2, 1, "HAN", "SOLDIER");

        // when
        withTransaction(connection -> {
                    movementRepository.save(
                            connection,
                            gameId,
                            Position.from(1, 1),
                            Position.from(2, 1),
                            capturedPiece
                    );
                    return null;
                }
        );

        // then
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                             SELECT 
                                 janggi_game_id,
                                 src_row_pos,
                                 src_col_pos,
                                 dest_row_pos,
                                 dest_col_pos,
                                 dest_team,
                                 dest_type 
                             FROM movement
                             """
             );
             ResultSet resultSet = statement.executeQuery()) {

            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getLong("janggi_game_id")).isEqualTo(gameId);
            assertThat(resultSet.getInt("src_row_pos")).isEqualTo(1);
            assertThat(resultSet.getInt("src_col_pos")).isEqualTo(1);
            assertThat(resultSet.getInt("dest_row_pos")).isEqualTo(2);
            assertThat(resultSet.getInt("dest_col_pos")).isEqualTo(1);
            assertThat(resultSet.getString("dest_team")).isEqualTo("HAN");
            assertThat(resultSet.getString("dest_type")).isEqualTo("SOLDIER");
        }
    }

}
