package janggi.repository.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.config.DatabaseManager;
import janggi.config.DdlAuto;
import janggi.config.TestConfig;
import janggi.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcMovementRepositoryTest {

    private final JdbcMovementRepository movementRepository = new JdbcMovementRepository();

    @BeforeAll
    static void setUp() {
        TestConfig.setUp();
    }

    @BeforeEach
    void setUpDatabase() {
        DatabaseManager.initTable(DdlAuto.CREATE_DROP);
        insertGame();
    }

    @Test
    void 이동_기록을_올바르게_저장한다() throws Exception {
        // when
        movementRepository.save(1L, Position.from(1, 1), Position.from(2, 1));
        movementRepository.save(1L, Position.from(1, 1), Position.from(2, 1));
        
        // then
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM movement");
             ResultSet resultSet = statement.executeQuery()) {

            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("count")).isEqualTo(2);
        }
    }

    private void insertGame() {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO janggi_game (turn, state) VALUES (?, ?)")) {
            statement.setString(1, "CHO");
            statement.setString(2, "PLAYING");
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("테스트용 게임 데이터 저장 실패", e);
        }
    }

}
