package persistence.integration;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.GameStatus;
import domain.JanggiGame;
import domain.Piece;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import factory.MoveStrategyFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.GameState;
import persistence.mapper.GameStateMapper;
import persistence.mapper.PieceStateMapper;
import persistence.repository.GameStateRepository;
import persistence.repository.JdbcGameStateRepository;

public class GamePersistenceIntegrationTest {

    private DataSource dataSource;
    private Connection connection;
    private GameStateRepository repository;
    private GameStateMapper gameStateMapper;

    private final MoveStrategyFactory moveStrategyFactory = new MoveStrategyFactory();

    @BeforeEach
    void setUp() throws Exception {
        dataSource = createDataSource();
        connection = dataSource.getConnection();
        createTables(connection);
        repository = new JdbcGameStateRepository(dataSource);
        gameStateMapper = new GameStateMapper(new PieceStateMapper());
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    @Test
    @DisplayName("장기 게임을 저장하고 다시 복원할 수 있다.")
    void save_and_restore_janggi_game() {
        JanggiGame original = createJanggiGame();

        GameState gameState = gameStateMapper.mapFrom(original);
        repository.save(gameState);

        Optional<GameState> loadedState = repository.load();
        JanggiGame restored = gameStateMapper.mapToJanggiGame(loadedState.get());

        assertThat(restored.gameStatus()).isEqualTo(original.gameStatus());
        assertThat(restored.allFactors().board().get(new Position(9, 1)).pieceProperty()).isEqualTo(
                new PieceProperty(PieceType.HORSE, Team.GREEN));
        assertThat(restored.allFactors().board().get(new Position(6, 0)).pieceProperty()).isEqualTo(
                new PieceProperty(PieceType.SOLDIER, Team.GREEN));
        assertThat(restored.allFactors().board().get(new Position(8, 4)).pieceProperty()).isEqualTo(
                new PieceProperty(PieceType.GENERAL, Team.GREEN));
    }

    private JanggiGame createJanggiGame() {
        Position horsePosition = new Position(9, 1);
        Position soldierPosition = new Position(6, 0);
        Position generalPosition = new Position(8, 4);

        PieceProperty horseGreenProperty = new PieceProperty(PieceType.HORSE, Team.GREEN);
        PieceProperty soldierGreenProperty = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
        PieceProperty generalGreenProperty = new PieceProperty(PieceType.GENERAL, Team.GREEN);

        Map<Position, Piece> testBoard = new HashMap<>();
        testBoard.put(horsePosition,
                Piece.of(horseGreenProperty, moveStrategyFactory.createMoveStrategy(horseGreenProperty)));
        testBoard.put(soldierPosition,
                Piece.of(soldierGreenProperty, moveStrategyFactory.createMoveStrategy(soldierGreenProperty)));
        testBoard.put(generalPosition,
                Piece.of(generalGreenProperty, moveStrategyFactory.createMoveStrategy(generalGreenProperty)));

        return JanggiGame.of(Board.of(testBoard), GameStatus.GREEN_PLAYER_TURN);
    }

    private DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:janggi;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private void createTables(Connection connection) throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/schema.sql"));
        List<String> statements = List.of(sql.split(";"));

        try (Statement statement = connection.createStatement()) {
            for (String each : statements) {
                if (each.isBlank()) {
                    continue;
                }
                statement.execute(each);
            }
        }
    }
}
