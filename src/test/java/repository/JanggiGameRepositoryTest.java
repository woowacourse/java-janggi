package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.JanggiGame;
import domain.game.JanggiGameFixture;
import domain.game.Side;
import dto.GameSummary;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.IntegrationTestSupport;

@DisplayName("리포지토리 계층 테스트")
class JanggiGameRepositoryTest extends IntegrationTestSupport {

    private JanggiGameRepository repository;
    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        conn = dataSource.getConnection();
        repository = new JanggiGameRepository();
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
