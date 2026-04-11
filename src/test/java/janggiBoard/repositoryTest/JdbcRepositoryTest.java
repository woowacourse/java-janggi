package janggiBoard.repositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.JdbcBoardRepository;
import domain.Position;
import domain.Team;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.strategy.PalaceStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.JdbcGameRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class JdbcRepositoryTest {
    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:9.0.0")
            .withDatabaseName("janggi_test")
            .withUsername("test")
            .withPassword("test");

    private JdbcBoardRepository jdbcBoardRepository;
    private JdbcGameRepository jdbcGameRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                MYSQL.getJdbcUrl(),
                MYSQL.getUsername(),
                MYSQL.getPassword()
        );

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS board (" +
                    "row_index INT, col_index INT, team VARCHAR(10), piece_type VARCHAR(20))");
            statement.execute("CREATE TABLE IF NOT EXISTS game_state (" +
                    "current_turn VARCHAR(10), is_finished BOOLEAN, cho_score DOUBLE, han_score DOUBLE)");
        }

        jdbcBoardRepository = new JdbcBoardRepository(connection);
        jdbcGameRepository = new JdbcGameRepository(connection);
    }

    @Test
    void 보드를_저장하고_다시_불러왔을때_정보가_일치해야한다() {
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = new Position(0, 0);
        board.put(position, new King(Team.CHO, new PalaceStrategy()));

        jdbcBoardRepository.save(board);
        Map<Position, Piece> result = jdbcBoardRepository.findAll();

        Assertions.assertThat(result.get(position).getPieceType()).isEqualTo(PieceType.KING);
    }

    @Test
    void 게임의_상태를_저장하고_다시_불러왔을떄_정보가_일치해야한다() {
        Team currentTurn = Team.HAN;
        boolean isFinished = false;
        double choScore = 10.0;
        double hanScore = 20.0;

        jdbcGameRepository.save(currentTurn, isFinished, choScore, hanScore);

        Team turn = jdbcGameRepository.findCurrentTurn();

        Assertions.assertThat(turn).isEqualTo(currentTurn);
    }
}
