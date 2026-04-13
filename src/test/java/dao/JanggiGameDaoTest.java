package dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import domain.GameStatus;
import domain.JanggiGame;
import dto.PieceSnapshot;
import factory.JanggiBoardFactory;
import infrastructure.TransactionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameDaoTest {

    private static DataSource dataSource;
    private static JanggiGameDao dao;
    private static JanggiGameStateDao stateDao;

    @BeforeAll
    static void setUpDataSource() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:janggi_dao_test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);
        TransactionContext.init(dataSource);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
        }

        dao = new JanggiGameDao();
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
    @DisplayName("게임을 저장하면 양수인 ID를 반환한다.")
    void insertGame_returns_positive_id() {
        List<PieceSnapshot> snapshots = JanggiGame.initGame(JanggiBoardFactory.initialBoard()).pieceSnapshots();

        long[] gameId = new long[1];
        TransactionContext.run(() -> gameId[0] = dao.insertGame(snapshots));

        assertThat(gameId[0]).isPositive();
    }

    @Test
    @DisplayName("저장된 기물을 게임 ID로 조회할 수 있다.")
    void findAllPieces_returns_saved_pieces() {
        List<PieceSnapshot> snapshots = JanggiGame.initGame(JanggiBoardFactory.initialBoard()).pieceSnapshots();

        long[] gameId = new long[1];
        TransactionContext.run(() -> gameId[0] = dao.insertGame(snapshots));

        List<PieceSnapshot> loaded = TransactionContext.query(() -> dao.findAllPieces(gameId[0]));

        assertThat(loaded).hasSize(snapshots.size());
    }

    @Test
    @DisplayName("진행 중인 게임이 없으면 빈 Optional을 반환한다.")
    void findLatestUnfinishedGameId_returns_empty_when_no_games() {
        Optional<Long> result = TransactionContext.query(dao::findLatestUnfinishedGameId);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("진행 중인 게임이 있으면 해당 게임 ID를 반환한다.")
    void findLatestUnfinishedGameId_returns_game_id() {
        List<PieceSnapshot> snapshots = JanggiGame.initGame(JanggiBoardFactory.initialBoard()).pieceSnapshots();
        String status = GameStatus.GREEN_PLAYER_TURN.description();

        long[] gameId = new long[1];
        TransactionContext.run(() -> {
            gameId[0] = dao.insertGame(snapshots);
            stateDao.insert(gameId[0], status, false);
        });

        Optional<Long> result = TransactionContext.query(dao::findLatestUnfinishedGameId);

        assertThat(result).contains(gameId[0]);
    }
}