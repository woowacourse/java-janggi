package dao;

import config.ConnectionFactory;
import dto.dao.InitialGamePersistDto;
import dto.dao.MovePersistDto;
import dto.dao.PiecePlacement;
import entity.GameEntity;
import entity.PieceEntity;
import entity.ResumableGameEntity;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import transaction.TransactionTemplate;

@Testcontainers(disabledWithoutDocker = true)
class GameDaoTest {
    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("janggi");

    private static ConnectionFactory connectionFactory;
    private static TransactionTemplate transactionTemplate;
    private GameDao gameDao;

    @BeforeAll
    static void beforeAll() throws Exception {
        MYSQL.start();
        connectionFactory = () -> DriverManager.getConnection(
                MYSQL.getJdbcUrl(), MYSQL.getUsername(), MYSQL.getPassword());
        transactionTemplate = new TransactionTemplate(connectionFactory);
        runSchema(connectionFactory);
    }

    private static void runSchema(ConnectionFactory factory) throws Exception {
        String ddl = readClasspathResource("schema.sql");
        try (Connection conn = factory.getConnection(); Statement st = conn.createStatement()) {
            executeStatements(st, ddl.split(";"));
        }
    }

    private static void executeStatements(Statement st, String[] statements) throws SQLException {
        for (String statement : statements) {
            String trimmed = statement.trim();
            if (!trimmed.isEmpty()) {
                st.execute(trimmed);
            }
        }
    }

    private static String readClasspathResource(String name) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = loader.getResourceAsStream(name)) {
            Assertions.assertThat(in).as("classpath 리소스: %s", name).isNotNull();
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static void truncateTables(ConnectionFactory factory) {
        try (Connection conn = factory.getConnection(); Statement st = conn.createStatement()) {
            st.execute("SET FOREIGN_KEY_CHECKS=0");
            st.execute("TRUNCATE TABLE piece");
            st.execute("TRUNCATE TABLE game");
            st.execute("SET FOREIGN_KEY_CHECKS=1");
        } catch (SQLException e) {
            throw new IllegalStateException("테스트 테이블 초기화 실패", e);
        }
    }

    @BeforeEach
    void setUp() {
        gameDao = new GameDao(connectionFactory);
        truncateTables(connectionFactory);
    }

    private long insertInitial(InitialGamePersistDto dto) {
        TransactionTemplate.Callback<Long> callback = conn -> gameDao.insertInitialGameAndPieces(conn, dto);
        return transactionTemplate.executeInTransaction(callback);
    }

    private void persistMove(MovePersistDto dto) {
        TransactionTemplate.Callback<Void> callback = conn -> {
            gameDao.persistMove(conn, dto);
            return null;
        };
        transactionTemplate.executeInTransaction(callback);
    }

    @Test
    @DisplayName("insertInitialGameAndPieces는 game·piece를 저장하고 생성된 game id를 반환한다.")
    void should_insert_initial_game_and_pieces() {
        List<PiecePlacement> placements = List.of(
                new PiecePlacement("CHO", "GENERAL", 0, 4),
                new PiecePlacement("HAN", "GENERAL", 9, 4)
        );

        long gameId = insertInitial(new InitialGamePersistDto(true, "CHO", 1.5, 2.5, placements));

        Assertions.assertThat(gameId).isPositive();
        Optional<Long> choGeneral = gameDao.findPieceIdAt(gameId, 0, 4);
        Optional<Long> hanGeneral = gameDao.findPieceIdAt(gameId, 9, 4);
        Assertions.assertThat(choGeneral).isPresent();
        Assertions.assertThat(hanGeneral).isPresent();
        Assertions.assertThat(choGeneral.get()).isNotEqualTo(hanGeneral.get());
    }

    @Test
    @DisplayName("findPieceIdAt는 해당 좌표에 기물이 없으면 empty를 반환한다.")
    void should_return_empty_when_no_piece_at_coordinates() {
        long gameId = insertInitial(new InitialGamePersistDto(true, "CHO", 0, 0,
                List.of(new PiecePlacement("CHO", "SOLDIER", 3, 3))));

        Assertions.assertThat(gameDao.findPieceIdAt(gameId, 0, 0)).isEmpty();
    }

    @Test
    @DisplayName("persistMove는 기물 좌표와 game 메타를 갱신한다.")
    void should_persist_move_without_capture() {
        long gameId = insertInitial(new InitialGamePersistDto(true, "CHO", 0, 0,
                List.of(new PiecePlacement("CHO", "SOLDIER", 3, 3))));
        long pieceId = gameDao.findPieceIdAt(gameId, 3, 3).orElseThrow();

        persistMove(new MovePersistDto(gameId, true, "HAN", 1.0, 2.0, null, pieceId, 3, 4, null));

        Assertions.assertThat(gameDao.findPieceIdAt(gameId, 3, 3)).isEmpty();
        Assertions.assertThat(gameDao.findPieceIdAt(gameId, 4, 3)).contains(pieceId);
        GameEntity state = gameDao.loadGameForResume(gameId);
        Assertions.assertThat(state.turnTeam()).isEqualTo("HAN");
        Assertions.assertThat(state.choScore()).isEqualTo(1.0);
        Assertions.assertThat(state.hanScore()).isEqualTo(2.0);
    }

    @Test
    @DisplayName("persistMove는 capturedPieceId가 있으면 해당 piece 행을 삭제한다.")
    void should_delete_captured_piece_on_persist_move() {
        long gameId = insertInitial(new InitialGamePersistDto(true, "CHO", 0, 0, List.of(
                new PiecePlacement("CHO", "SOLDIER", 3, 3),
                new PiecePlacement("HAN", "SOLDIER", 4, 3)
        )));
        long choId = gameDao.findPieceIdAt(gameId, 3, 3).orElseThrow();
        long hanId = gameDao.findPieceIdAt(gameId, 4, 3).orElseThrow();

        persistMove(new MovePersistDto(gameId, true, "HAN", 0, 0, null, choId, 3, 4, hanId));

        Assertions.assertThat(gameDao.findPieceIdAt(gameId, 4, 3)).contains(choId);
        Assertions.assertThat(gameDao.findPieceIdAt(gameId, 3, 3)).isEmpty();
        List<PieceEntity> pieces = gameDao.loadGameForResume(gameId).pieces();
        Assertions.assertThat(pieces).hasSize(1);
    }

    @Test
    @DisplayName("loadGameForResume는 점수·차례·기물 목록을 반환한다.")
    void should_load_game_state_for_resume() {
        long gameId = insertInitial(new InitialGamePersistDto(true, "HAN", 10.5, 20.5,
                List.of(new PiecePlacement("CHO", "CHARIOT", 0, 0))));

        GameEntity state = gameDao.loadGameForResume(gameId);

        Assertions.assertThat(state.gameId()).isEqualTo(gameId);
        Assertions.assertThat(state.choScore()).isEqualTo(10.5);
        Assertions.assertThat(state.hanScore()).isEqualTo(20.5);
        Assertions.assertThat(state.turnTeam()).isEqualTo("HAN");
        Assertions.assertThat(state.pieces()).hasSize(1);
        PieceEntity p = state.pieces().getFirst();
        Assertions.assertThat(p.team()).isEqualTo("CHO");
        Assertions.assertThat(p.pieceType()).isEqualTo("CHARIOT");
        Assertions.assertThat(p.y()).isZero();
        Assertions.assertThat(p.x()).isZero();
    }

    @Test
    @DisplayName("findResumableGames는 winner_team이 NULL인 게임만 반환한다.")
    void should_list_only_resumable_games() throws SQLException {
        long openId = insertInitial(new InitialGamePersistDto(true, "CHO", 0, 0,
                List.of(new PiecePlacement("CHO", "GENERAL", 0, 4))));
        try (Connection conn = connectionFactory.getConnection(); Statement st = conn.createStatement()) {
            st.executeUpdate(
                    "INSERT INTO game (status, turn_team, cho_score, han_score, winner_team) "
                            + "VALUES (0, 'HAN', 0, 0, 'CHO')"
            );
        }

        List<ResumableGameEntity> list = gameDao.findResumableGames();

        Assertions.assertThat(list).extracting(ResumableGameEntity::id).containsExactly(openId);
    }

    @Test
    @DisplayName("persistMove시 승자가 정해지면 winner_team이 갱신되고 재개 목록에서 제외되어야 한다.")
    void should_update_winner_and_exclude_from_resumable_when_game_ends() throws SQLException {
        long gameId = insertInitial(new InitialGamePersistDto(true, "CHO", 0, 0,
                List.of(new PiecePlacement("CHO", "SOLDIER", 3, 3))));
        long pieceId = gameDao.findPieceIdAt(gameId, 3, 3).orElseThrow();

        persistMove(new MovePersistDto(gameId, false, "HAN", 0.0, 0.0, "CHO", pieceId, 4, 3, null));

        List<ResumableGameEntity> resumables = gameDao.findResumableGames();
        Assertions.assertThat(resumables).extracting(ResumableGameEntity::id).doesNotContain(gameId);

        try (Connection conn = connectionFactory.getConnection(); Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT winner_team FROM game WHERE id = " + gameId)) {
            Assertions.assertThat(rs.next()).isTrue();
            Assertions.assertThat(rs.getString("winner_team")).isEqualTo("CHO");
        }
    }

    @Test
    @DisplayName("존재하지 않거나 불러올 수 없는 게임 ID를 조회하면 IllegalStateException 예외가 발생한다.")
    void should_throw_exception_when_loading_invalid_game() {
        Assertions.assertThatThrownBy(() -> gameDao.loadGameForResume(99999L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("재개할 수 없는 게임입니다");
    }
}
