package repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.JanggiGameFixture;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import dto.GameSummary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @DisplayName("트랜잭션 테스트")
    @Nested
    class 트랜잭션_테스트 {

        @DisplayName("트랜잭션을 모두 완료하지 못하면, 해당 트랜잭션 자체를 취소(롤백)한다")
        @Test
        void rollback() throws SQLException {
            // given
            JanggiGameRepository spyGameRepository = spy(repository);
            doThrow(IllegalStateException.class)
                    .when(spyGameRepository).syncPieces(
                            any(Connection.class),
                            any(JanggiGame.class),
                            anyLong()
                    );

            Side expectedCurrentTurn = Side.CHO;
            JanggiGame janggiGame = JanggiGame.create(
                    new Board(new AlivePieces(
                            Map.of(new Intersection(10, 1),
                                    Piece.of(PieceType.CHARIOT, expectedCurrentTurn)))
                    )
            );
            long gameIdA = spyGameRepository.save(conn, janggiGame);
            conn.commit();
            System.out.println("gameIdA = " + gameIdA);

            // when and then
            janggiGame.movePiece(new Intersection(10, 1), new Intersection(9, 1), expectedCurrentTurn);

            conn.setAutoCommit(false);
            try {
                spyGameRepository.updateGameStatus(conn, janggiGame, gameIdA);
            } catch (IllegalStateException e) {
                // [중요] 예외가 발생했을 때 테스트 코드에서 직접 롤백을 수행함
                System.out.println("CALLBACK IN CATCH");
                conn.rollback();
            }

            System.out.println("BEFORE CALL: findById, gameId=" + gameIdA);
            JanggiGame foundGame = spyGameRepository.findById(conn, gameIdA);
            Side actualCurrentTurn = foundGame.currentTurn();
            assertThat(actualCurrentTurn).isEqualTo(expectedCurrentTurn);
        }

        @DisplayName("트랜잭션을 모두 완료하면, 해당 트랜잭션을 반영(커밋)한다")
        @Test
        void commit() {
            // given
            Side previousTurn = Side.CHO;
            JanggiGame janggiGame = JanggiGame.create(
                    new Board(new AlivePieces(
                            Map.of(new Intersection(10, 1),
                                    Piece.of(PieceType.CHARIOT, previousTurn)))
                    )
            );
            long gameIdA = repository.save(conn, janggiGame);

            // when
            janggiGame.movePiece(new Intersection(10, 1), new Intersection(9, 1), previousTurn);
            repository.updateGameStatus(conn, janggiGame, gameIdA);
            JanggiGame foundGame = repository.findById(conn, gameIdA);
            Side actualCurrentTurn = foundGame.currentTurn();

            // then
            assertThat(actualCurrentTurn).isEqualTo(previousTurn.nextTurn());
        }
    }
}
