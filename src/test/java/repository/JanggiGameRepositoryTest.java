package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.JanggiGame;
import domain.game.JanggiGameFixture;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameRepositoryTest {

    private static final Path TEST_SCHEMA_PATH = Path.of("src/test/resources/test_schema.sql");
    private static final String QUERY_DELIMITER = ";";

    private JanggiGameRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        DataSource dataSource = MemoryDBConnectionUtil.getDataSource();
        repository = new JanggiGameRepository(dataSource);

        String[] queries = Files.readString(TEST_SCHEMA_PATH)
                .trim()
                .split(QUERY_DELIMITER);

        try (
                Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement()
        ) {
            for (String query : queries) {
                statement.execute(query);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @DisplayName("게임을 저장한 후 ID로 조회한다")
    @Test
    void 게임_저장_조회() {
        JanggiGame janggiGame = JanggiGameFixture.create_game_with_sufficient_points_and_both_general_uncaptured();

        long gameId = repository.save(janggiGame);
        JanggiGame found = repository.findById(gameId);

        assertThat(janggiGame)
                .usingRecursiveComparison()
                .isEqualTo(found);
    }
}
