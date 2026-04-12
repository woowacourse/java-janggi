package persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
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
        Optional<GameState> actual = repository.load();

        assertThat(actual.get().gameStatus()).isEqualTo(expected.gameStatus());
        assertThat(actual.get().pieceStates()).containsExactlyInAnyOrderElementsOf(expected.pieceStates());
    }

    @Test
    @DisplayName("저장된 게임이 없다면 빈 Optional을 반환한다.")
    void no_save_and_load_game_state_empty_optional_test() {
        Optional<GameState> actual = repository.load();

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("저장된 게임이 없으면 false를 반환한다.")
    void nothing_save_game_return_false_test() {
        boolean actual = repository.exist();

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("저장된 게임이 있으면 true를 반환한다.")
    void have_save_game_return_true_test() {
        repository.save(createGameState());

        boolean actual = repository.exist();

        assertThat(actual).isTrue();
    }

    private DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + UUID.randomUUID());
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
