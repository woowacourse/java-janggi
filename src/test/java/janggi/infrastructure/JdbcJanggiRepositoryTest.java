package janggi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Board;
import janggi.domain.game.Players;
import janggi.domain.game.PlayersSaveDTO;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JdbcJanggiRepositoryTest {
    private static DataSource dataSource;
    private JdbcJanggiRepository repository;

    @BeforeAll
    static void initAll() {
        dataSource = new TestDataSource(
                "jdbc:h2:mem:janggi;MODE=MySQL;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // game 테이블 생성
            stmt.execute("CREATE TABLE IF NOT EXISTS game (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "cho_player VARCHAR(50) NOT NULL, " +
                    "han_player VARCHAR(50) NOT NULL, " +
                    "current_turn VARCHAR(10) NOT NULL, " +
                    "is_finished BOOLEAN DEFAULT FALSE, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // board_state 테이블 생성
            stmt.execute("CREATE TABLE IF NOT EXISTS board_state (" +
                    "game_id BIGINT, " +
                    "row_index INT, " +
                    "col_index INT, " +
                    "piece_type VARCHAR(20), " +
                    "side VARCHAR(10), " +
                    "piece_number VARCHAR(10), " +
                    "PRIMARY KEY (game_id, row_index, col_index), " +
                    "FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE)");

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 스키마 초기화 실패: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        repository = new JdbcJanggiRepository(dataSource);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            // 제약 조건을 끄고 전체 삭제
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            stmt.execute("TRUNCATE TABLE board_state");
            stmt.execute("TRUNCATE TABLE game");
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("새 게임을 시작하는 경우, 게임 ID가 생성된다.")
    @Test
    void 새_게임_시작_게임_아이디_생성_테스트() {
        // given
        PlayersSaveDTO dto = new PlayersSaveDTO("pobi", "jason", "CHO");

        // when
        Long gameId = repository.save(dto);

        // then
        assertThat(gameId).isNotNull();
        assertThat(gameId).isGreaterThan(0);
    }

    @DisplayName("턴이 끝날 때마다, 게임 상태를 저장한다.")
    @Test
    void 턴_종료_시마다_게임_상태_저장_테스트() {
        // given
        Players players = Players.createInitial("pobi", "jason");
        Long gameId = repository.save(PlayersSaveDTO.from(players));
        Board board = Board.initialize();
        Turn turn = Turn.from(Side.HAN);

        // when
        repository.updateGameStatus(gameId, board, turn);

        // then
        assertThat(repository.findBoardById(gameId)).isEqualTo(board);
        assertThat(repository.findTurnById(gameId)).isEqualTo(turn);
        assertThat(repository.findPlayersById(gameId)).isEqualTo(players);
    }

    @DisplayName("진행 중인 가장 최근 게임을 조회한다.")
    @Test
    void 진행_중인_가장_최근_게임_조회_테스트() {
        // given
        repository.save(PlayersSaveDTO.from(Players.createInitial("pobi", "jason")));
        Long secondGameId = repository.save(PlayersSaveDTO.from(Players.createInitial("gugu", "lisa")));

        // when
        Optional<Long> lastGameId = repository.findInProgressGameId();

        // then
        assertThat(lastGameId).isPresent();
        assertThat(lastGameId.get()).isEqualTo(secondGameId);
    }

    @DisplayName("ID에 맞는 보드를 조회한다.")
    @Test
    void 게임_아이디로_보드_조회_테스트() {
        // given
        Long gameId = repository.save(PlayersSaveDTO.from(Players.createInitial("pobi", "jason")));
        Board board = Board.initialize();
        repository.updateGameStatus(gameId, board, Turn.from(Side.CHO));

        // when
        Board foundBoard = repository.findBoardById(gameId);

        // then
        assertThat(foundBoard).isEqualTo(board);
    }

    @DisplayName("데이터가 없는 ID로 보드를 조회하는 경우, NoSuchElementException이 발생한다.")
    @Test
    void 게임_아이디로_보드_조회_예외_테스트() {
        assertThatThrownBy(() -> repository.findBoardById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("[ERROR] 해당 ID의 게임을 찾을 수 없습니다.");
    }

    @DisplayName("ID에 맞는 플레이어를 조회한다.")
    @Test
    void 게임_아이디로_플레이어_조회_테스트() {
        // given
        Players players = Players.createInitial("pobi", "jason");
        PlayersSaveDTO dto = PlayersSaveDTO.from(players);
        Long gameId = repository.save(dto);

        // when
        Players foundPlayers = repository.findPlayersById(gameId);

        // then
        assertThat(foundPlayers).isEqualTo(players);
    }

    @DisplayName("데이터가 없는 ID로 플레이어를 조회하는 경우, RuntimeException이 발생한다.")
    @Test
    void 게임_아이디로_플레이어_조회_예외_테스트() {
        assertThatThrownBy(() -> repository.findPlayersById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[ERROR] 해당 ID의 게임을 찾을 수 없습니다.");
    }

    @DisplayName("ID에 맞는 턴을 조회한다.")
    @Test
    void 게임_아이디로_턴_조회_테스트() {
        // given
        Long gameId = repository.save(PlayersSaveDTO.from(Players.createInitial("pobi", "jason")));
        Turn turn = Turn.from(Side.CHO);
        repository.updateGameStatus(gameId, Board.initialize(), turn);

        // when
        Turn foundTurn = repository.findTurnById(gameId);

        // then
        assertThat(foundTurn).isEqualTo(turn);
    }

    @DisplayName("게임을 종료한다.")
    @Test
    void 게임_종료_테스트() {
        // given
        Long gameId = repository.save(PlayersSaveDTO.from(Players.createInitial("pobi", "jason")));

        // when
        repository.finishGame(gameId);

        // then
        assertThat(repository.findInProgressGameId()).isEmpty();
    }

    @DisplayName("한(HAN) 진영 턴에서 중단된 게임을 재시작하는 경우, 한(HAN) 진영 턴으로 복구된다.")
    @Test
    void 게임_재시작_시_턴_교체_테스트() {
        // given
        Players initialPlayers = Players.createInitial("pobi", "jason");
        Long gameId = repository.save(PlayersSaveDTO.from(initialPlayers)); // CHO 턴으로 저장

        // when
        Board board = Board.initialize();
        Turn hanTurn = Turn.from(Side.HAN);
        repository.updateGameStatus(gameId, board, hanTurn);
        Players loadedPlayers = repository.findPlayersById(gameId);

        // then
        assertThat(loadedPlayers.getTurn().getSide()).isEqualTo(Side.HAN);
    }
}
