package domain.repository;

import domain.Game;
import domain.entity.GameRoomEntity;
import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Chariot;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.state.ChuSide;
import domain.state.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.DatabaseConnector;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRepositoryTest {

    private final JdbcGameRepository repository = new JdbcGameRepository(new GameRoomDao(), new BoardStateDao());

    @BeforeEach
    void setUp() {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE board_state");
            stmt.executeUpdate("TRUNCATE TABLE game_room");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE board_state");
            stmt.executeUpdate("TRUNCATE TABLE game_room");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("새로운 게임을 저장하면 DB에서 생성된 ID를 할당받아야 한다")
    void saveNewGameTest() {
        // given
        Game game = createSampleGame();

        // when
        repository.save(game);

        // then
        assertThat(game.getId()).isNotNull();
    }

    @Test
    @DisplayName("저장된 게임을 ID로 조회하면 동일한 상태의 게임이 로드되어야 한다")
    void loadGameTest() {
        // given
        Game originalGame = createSampleGame();
        repository.save(originalGame);
        Long savedId = originalGame.getId();

        // when
        Optional<Game> loadedGameOpt = repository.load(savedId);

        // then
        assertThat(loadedGameOpt).isPresent();
        Game loadedGame = loadedGameOpt.get();
        assertThat(loadedGame.getId()).isEqualTo(savedId);
        assertThat(loadedGame.getSide()).isEqualTo(Side.CHU);
    }

    @Test
    @DisplayName("게임의 턴을 변경하고 다시 저장하면 업데이트가 반영되어야 한다")
    void updateGameTest() {
        // given
        Game game = createSampleGame();
        repository.save(game);
        Long savedId = game.getId();

        // then
        Game loadedGame = repository.load(savedId).get();
        assertThat(loadedGame.getSide()).isEqualTo(Side.CHU);
    }

    @Test
    @DisplayName("모든 게임방 목록을 조회할 수 있어야 한다")
    void findAllRoomsTest() {
        // given
        repository.save(createSampleGame());
        repository.save(createSampleGame());

        // when
        List<GameRoomEntity> rooms = repository.findAllRooms();

        // then
        assertThat(rooms).hasSize(2);
    }

    private Game createSampleGame() {
        Map<Position, Piece> pieceMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                pieceMap.put(Position.of(i, j), EmptyPiece.getInstance());
            }
        }
        pieceMap.put(Position.of(0, 0), new Chariot(Side.CHU));

        return new Game(new Board(pieceMap), new ChuSide());
    }
}
