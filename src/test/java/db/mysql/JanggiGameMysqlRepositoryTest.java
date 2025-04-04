package db.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import domain.GameState;
import domain.Team;
import fixture.TestContainer;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGameMysqlRepositoryTest {

    private final JanggiGameMysqlRepository repository = new JanggiGameMysqlRepository();
    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        TestContainer.truncateAll();
        conn = TestContainer.getConnection();
    }

    @Test
    void 턴을_불러온다() {

        // given
        // when
        final Team turn = repository.getTurn(conn);

        // then
        assertThat(turn).isEqualTo(Team.GREEN);
    }

    @Test
    void 게임_상태를_불러온다() {

        // given
        // when
        repository.updateGameState(conn, GameState.RUNNING);
        final GameState gameState = repository.getGameState(conn);

        // then
        assertThat(gameState).isEqualTo(GameState.RUNNING);
    }

    @Test
    void 턴을_변경한다() {
        
        // given
        // when
        repository.changeTurn(conn, Team.GREEN);
        final Team turn = repository.getTurn(conn);

        // then
        assertThat(turn).isEqualTo(Team.GREEN);
    }
}
