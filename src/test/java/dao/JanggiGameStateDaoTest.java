package dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import domain.GameStatus;
import domain.JanggiGame;
import dto.GameStateData;
import java.util.Optional;
import dto.PieceSnapshot;
import factory.JanggiBoardFactory;
import infrastructure.TransactionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameStateDaoTest {

    private static DataSource dataSource;
    private static JanggiGameDao gameDao;
    private static JanggiGameStateDao stateDao;

    @BeforeAll
    static void setUpDataSource() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:janggi_state_test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);
        TransactionContext.init(dataSource);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
        }

        gameDao = new JanggiGameDao();
        stateDao = new JanggiGameStateDao();
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
    @DisplayName("게임 상태를 저장하면 같은 상태로 조회된다.")
    void insert_then_findByGameId_returns_correct_state() {
        List<PieceSnapshot> snapshots = JanggiGame.initGame(JanggiBoardFactory.initialBoard()).pieceSnapshots();
        String status = GameStatus.GREEN_PLAYER_TURN.description();

        long[] gameId = new long[1];
        TransactionContext.run(() -> {
            gameId[0] = gameDao.insertGame(snapshots);
            stateDao.insert(gameId[0], status, false);
        });

        Optional<GameStateData> loaded = TransactionContext.query(() -> stateDao.findByGameId(gameId[0]));

        assertThat(loaded).isPresent();
        assertThat(loaded.get().currentState()).isEqualTo(status);
        assertThat(loaded.get().isFinished()).isFalse();
    }
}