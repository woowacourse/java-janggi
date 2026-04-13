package janggi.db;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.GameContext;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import janggi.repository.GameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DbTest {

    private final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private final H2DBConnector h2DBConnector = new H2DBConnector(TEST_URL);
    private final int FIX_GAME_ID = 1;
    private GameRepository gameRepository;

    @BeforeEach
    void setup() throws SQLException {
        this.gameRepository = new GameRepository(h2DBConnector);
        try (Connection connection = h2DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS game (id INT PRIMARY KEY, current_turn VARCHAR(10));");
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS piece (id INT AUTO_INCREMENT PRIMARY KEY, game_id INT, position_row INT, position_column INT, piece_type VARCHAR(50), team_type VARCHAR(50), FOREIGN KEY (game_id) REFERENCES game(id));");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = h2DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS piece;");
            statement.execute("DROP TABLE IF EXISTS game;");
        }
    }

    @Test
    @DisplayName("게임 저장 후 불러오면 현재 차례가 불러와진다.")
    void success1() throws SQLException {
        Map<Position, Piece> positionPieces = new HashMap<>();
        Board board = new Board(positionPieces);
        TurnManager turnManager = new TurnManager();
        turnManager.changeTurn();
        GameContext gameContext = new GameContext(turnManager, board);
        gameRepository.saveGame(gameContext, FIX_GAME_ID);

        GameContext gameContextTest = gameRepository.loadPreviousGame(FIX_GAME_ID);
        assertThat(gameContextTest.currentTeamType()).isEqualTo(turnManager.currentTeamType());
    }

    @Test
    @DisplayName("게임 저장 후 불러오면 현재 보드의 기물들이 불러와진다.")
    void success2() {
        Map<Position, Piece> positionPieces = Map.of(
                Position.valueOf(10, 5), new Cannon(TeamType.RED),
                Position.valueOf(9, 5), new Soldier(TeamType.BLUE),
                Position.valueOf(9, 6), new General(TeamType.BLUE),
                Position.valueOf(1, 5), new General(TeamType.RED)
        );
        Map<Position, Piece> expected = Map.copyOf(positionPieces);
        Board board = new Board(positionPieces);
        GameContext gameContext = new GameContext(new TurnManager(), board);
        gameRepository.saveGame(gameContext, FIX_GAME_ID);

        GameContext gameContextTest = gameRepository.loadPreviousGame(FIX_GAME_ID);
        Map<Position, Piece> actual = gameContextTest.getPositionPieceMap();
        assertThat(actual).containsAllEntriesOf(expected);
    }
}
