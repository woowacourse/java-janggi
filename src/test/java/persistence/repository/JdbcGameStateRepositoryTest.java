package persistence.repository;

import domain.GameStatus;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.GameState;
import persistence.entity.PieceState;

class JdbcGameStateRepositoryTest {

    private DataSource dataSource;
    private Connection connection;
    private GameStateRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        dataSource = createDataSource();
        connection = dataSource.getConnection();
        createTables(connection);
        repository = new JdbcGameStateRepository(dataSource);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    @Test
    @DisplayName("게임 상태 저장/조회 검증 테스트")
    void save_and_load_game_state_test() {
        GameState expected = createGameState();

        repository.save(expected);
        GameState actual = repository.load();

        Assertions.assertThat(actual.gameStatus()).isEqualTo(expected.gameStatus());
        Assertions.assertThat(actual.pieceStates()).containsExactlyInAnyOrderElementsOf(expected.pieceStates());
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

    private GameState createGameState() {
        Position horsePosition = new Position(9, 1);
        Position soldierPosition = new Position(6, 0);
        Position generalPosition = new Position(8, 4);

        PieceProperty horseGreenProperty = new PieceProperty(PieceType.HORSE, Team.GREEN);
        PieceProperty soldierGreenProperty = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
        PieceProperty generalGreenProperty = new PieceProperty(PieceType.GENERAL, Team.GREEN);

        PieceState horseState = new PieceState(horseGreenProperty, horsePosition);
        PieceState soldierState = new PieceState(soldierGreenProperty, soldierPosition);
        PieceState generalState = new PieceState(generalGreenProperty, generalPosition);

        return new GameState(List.of(horseState, soldierState, generalState), GameStatus.GREEN_PLAYER_TURN);
    }

}
