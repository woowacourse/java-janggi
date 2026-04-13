package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dao.JanggiGameDao;
import dao.JanggiGameStateDao;
import domain.GameStatus;
import domain.Position;
import exception.JanggiBusinessException;
import infrastructure.TransactionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.JdbcJanggiRepository;

class JanggiServiceIntegrationTest {

    private static DataSource dataSource;
    private static JanggiCommandService commandService;
    private static JanggiQueryService queryService;

    @BeforeAll
    static void setUpDataSource() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:janggi_service_test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);
        TransactionContext.init(dataSource);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
        }

        JdbcJanggiRepository repository = new JdbcJanggiRepository(new JanggiGameDao(), new JanggiGameStateDao());
        commandService = new JanggiCommandService(repository);
        queryService = new JanggiQueryService(repository);
    }

    @BeforeEach
    void clearData() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM piece");
            stmt.execute("DELETE FROM game_state");
            stmt.execute("DELETE FROM game");
        }
    }

    @Test
    @DisplayName("새 게임을 생성하면 진행 중인 게임으로 조회된다.")
    void setupGame_then_isInProgress() {
        long gameId = commandService.setupGame();

        assertThat(queryService.isInProgress(gameId)).isTrue();
    }

    @Test
    @DisplayName("새 게임의 첫 턴은 초나라 차례이다.")
    void setupGame_first_turn_is_green() {
        long gameId = commandService.setupGame();

        assertThat(queryService.currentPlayerTurn(gameId))
                .isEqualTo(GameStatus.GREEN_PLAYER_TURN.description());
    }

    @Test
    @DisplayName("move 후 상대방 턴으로 바뀐다.")
    void move_changes_player_turn() {
        long gameId = commandService.setupGame();
        String beforeTurn = queryService.currentPlayerTurn(gameId);

        commandService.move(gameId, Position.of(6, 2), Position.of(5, 2));

        assertThat(queryService.currentPlayerTurn(gameId)).isNotEqualTo(beforeTurn);
    }

    @Test
    @DisplayName("진행 중인 게임이 있으면 hasUnfinishedGameId는 true를 반환한다.")
    void hasUnfinishedGameId_returns_true_after_setup() {
        commandService.setupGame();

        assertThat(queryService.hasUnfinishedGameId()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 게임 ID로 조회하면 예외를 던진다.")
    void load_non_existent_game_throws_exception() {
        assertThatThrownBy(() -> queryService.isInProgress(999L))
                .isExactlyInstanceOf(JanggiBusinessException.class);
    }
}