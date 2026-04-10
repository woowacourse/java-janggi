package janggi.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.dto.GameSessionDto;
import janggi.persistence.dao.BoardDao;
import janggi.persistence.dao.GameDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {

    private JdbcGameRepository repository;

    @BeforeEach
    void setUp() {
        GameDao fakeGameDao = createFakeGameDao();
        BoardDao fakeBoardDao = createFakeBoardDao();
        repository = new JdbcGameRepository(fakeGameDao, fakeBoardDao);
    }

    @Test
    @DisplayName("DAO들로부터 데이터를 받아 완전한 GameManager 객체로 조립한다")
    void findByGameIdAssemblesGameManager() throws SQLException {
        GameManager manager = repository.findByGameId(null, 1L);
        assertThat(manager.getId()).isEqualTo(1L);
        assertThat(manager.currentPlayer().side().name()).isEqualTo("CHO");
        assertThat(manager.getBoard()).isNotNull();
    }

    @Test
    @DisplayName("저장 시 신규 게임이면 DAO의 insert와 보드 갱신을 순차적으로 호출한다")
    void saveNewGameDelegatesToDaos() throws SQLException {
        GameManager newManager = GameManager.loadGame(Players.from("초", "한"), Board.initialize(), null);
        GameManager savedManager = repository.save(null, newManager);
        assertThat(savedManager.getId()).isEqualTo(1L);
    }

    private GameDao createFakeGameDao() {
        return new GameDao() {
            @Override
            public long insert(Connection connection, GameManager gameManager) {
                return 1L;
            }

            @Override
            public void update(Connection connection, GameManager gameManager) {
            }

            @Override
            public GameSessionDto findById(Connection connection, long gameId) {
                return new GameSessionDto(1L, "초", "한", "CHO", LocalDateTime.now());
            }

            @Override
            public List<GameSessionDto> findAllActive(Connection connection) {
                return List.of();
            }
        };
    }

    private BoardDao createFakeBoardDao() {
        return new BoardDao() {
            @Override
            public void deleteByGameId(Connection connection, long gameId) {
            }

            @Override
            public void insertAll(Connection connection, long gameId, Board board) {
            }

            @Override
            public Board findByGameId(Connection connection, long gameId) {
                return Board.initialize();
            }
        };
    }
}
