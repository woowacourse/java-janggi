package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.JanggiGame;
import domain.game.JanggiGameFixture;
import domain.game.Side;
import dto.GameSummary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("리포지토리 계층 테스트")
class JanggiGameRepositoryTest {

    private static final Path TEST_SCHEMA_PATH = Path.of("src/test/resources/test_schema.sql");
    private static final String QUERY_DELIMITER = ";";

    private JanggiGameRepository repository;
    private Connection conn;

    @BeforeEach
    void setUp() throws IOException, SQLException {
        conn = MemoryDBConnectionUtil.getDataSource().getConnection();
        conn.setAutoCommit(false);

        DataSource dataSource = MemoryDBConnectionUtil.getDataSource();
        repository = new JanggiGameRepository(dataSource);

        String[] queries = Files.readString(TEST_SCHEMA_PATH)
                .trim()
                .split(QUERY_DELIMITER);

        try (
                Connection conn2 = dataSource.getConnection();
                Statement statement = conn2.createStatement()
        ) {
            for (String query : queries) {
                statement.execute(query);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();
    }

    @DisplayName("게임을 저장한 후 ID로 조회한다")
    @Test
    void 게임_저장_조회() {
        JanggiGame janggiGame = JanggiGameFixture.create_game_with_sufficient_points_and_both_general_uncaptured();

        long gameId = repository.save(conn, janggiGame);
        JanggiGame found = repository.findById(conn, gameId);

        assertThat(janggiGame)
                .usingRecursiveComparison()
                .isEqualTo(found);
    }

    @DisplayName("저장된 게임들의 요약 정보를 조회한다")
    @Test
    void 게임_목록_조회() {
        long gameIdA = repository.save(conn,
                JanggiGameFixture.create_game_with_sufficient_points_and_both_general_uncaptured()
        );
        long gameIdB = repository.save(conn,
                JanggiGameFixture.create_game_with_sufficient_points_only_one_side(Side.CHO)
        );

        List<GameSummary> gameSummaries = repository.findAll(conn);

        assertThat(gameSummaries)
                .hasSize(2)
                .extracting(GameSummary::id)
                .containsExactlyInAnyOrder(gameIdA, gameIdB);
    }
}
