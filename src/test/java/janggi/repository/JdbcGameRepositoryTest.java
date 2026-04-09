package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.InSetUp;
import janggi.domain.game.Game;
import janggi.domain.side.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {

    private static final String URL = "jdbc:sqlite:file:testdb?mode=memory&cache=shared";

    private Connection keepAlive;
    private TransactionManager transactionManager;
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() throws SQLException {
        this.keepAlive = java.sql.DriverManager.getConnection(URL);
        JdbcContext jdbcContext = new JdbcContext(URL);
        this.transactionManager = new TransactionManager(jdbcContext);
        this.gameRepository = new JdbcGameRepository();
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (keepAlive != null) {
            keepAlive.close();
        }
    }

    @Test
    @DisplayName("save(): 게임을 저장하면 게임 ID가 반환된다")
    void save() {
        Game game = Game.createGame(new InSetUp(), new InSetUp());

        int gameId = transactionManager.execute(() -> gameRepository.save(game));

        assertThat(gameId).isPositive();
    }

    @Test
    @DisplayName("findActiveGameId(): 저장 후 진행 중인 게임 ID를 조회한다")
    void findActiveGameId() {
        Game game = Game.createGame(new InSetUp(), new InSetUp());
        int savedId = transactionManager.execute(() -> gameRepository.save(game));

        Optional<Integer> activeId = transactionManager.execute(() -> gameRepository.findActiveGameId());

        assertThat(activeId).isPresent();
        assertThat(activeId.get()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("load(): 저장한 게임을 불러오면 턴 정보가 일치한다")
    void load_turns() {
        Game game = Game.createGame(new InSetUp(), new InSetUp());
        game.move(Point.of(0, 0), Point.of(1, 0));

        int gameId = transactionManager.execute(() -> gameRepository.save(game));
        Game loaded = transactionManager.execute(() -> gameRepository.load(gameId));

        assertThat(loaded.getTurn()).isEqualTo(Side.HAN);
    }

    @Test
    @DisplayName("load(): 저장한 게임을 불러오면 기물 배치가 일치한다")
    void load_pieces() {
        Game game = Game.createGame(new InSetUp(), new InSetUp());
        int gameId = transactionManager.execute(() -> gameRepository.save(game));

        Game loaded = transactionManager.execute(() -> gameRepository.load(gameId));

        assertThat(loaded.getBoard()).containsKey(Point.of(0, 0));
        assertThat(loaded.getBoard().get(Point.of(0, 0)).getSide()).isEqualTo(Side.CHO);
    }

    @Test
    @DisplayName("update(): update 후 로드하면 변경된 보드 상태를 반환한다")
    void update() {
        Game game = Game.createGame(new InSetUp(), new InSetUp());
        int gameId = transactionManager.execute(() -> gameRepository.save(game));

        game.move(Point.of(0, 0), Point.of(1, 0));
        transactionManager.execute(() -> gameRepository.update(gameId, game));

        Game loaded = transactionManager.execute(() -> gameRepository.load(gameId));

        assertThat(loaded.getBoard()).doesNotContainKey(Point.of(0, 0));
        assertThat(loaded.getBoard()).containsKey(Point.of(1, 0));
    }

    @Test
    @DisplayName("finish(): 게임이 종료되면 게임 진행 상태에서 종료 상태가 된다")
    void finish() {
        Game game = Game.createGame(new InSetUp(), new InSetUp());
        int gameId = transactionManager.execute(() -> gameRepository.save(game));

        transactionManager.execute(() -> gameRepository.finish(gameId, Side.CHO));

        Optional<Integer> activeId = transactionManager.execute(() -> gameRepository.findActiveGameId());
        assertThat(activeId).isEmpty();
    }
}
