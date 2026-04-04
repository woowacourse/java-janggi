package db.repository;

import static org.assertj.core.api.Assertions.assertThat;

import board.SangSetupType;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseInitializer;
import db.jdbc.JdbcBoardPieceDao;
import db.jdbc.JdbcGameDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import position.Position;

class JdbcJanggiGameRepositoryTest {

    private ConnectionManager connectionManager;
    private JanggiGameRepository repository;

    @BeforeEach
    void setUp() {
        connectionManager = new ConnectionManager();
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);
        databaseInitializer.initialize();
        clearDatabase();

        GameDao gameDao = new JdbcGameDao(connectionManager);
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao(connectionManager);
        repository = new JdbcJanggiGameRepository(gameDao, boardPieceDao);
    }

    @Test
    void 게임을_저장한_후_다시_조회할_수_있다() {
        // given
        JanggiGame game = JanggiGame.of(SangSetupType.LEFT_SANG_SETUP, SangSetupType.RIGHT_SANG_SETUP);
        // when
        Long gameId = repository.save(game);
        Optional<JanggiGame> found = repository.findById(gameId);
        // then
        assertThat(found).isPresent();
        JanggiGame foundGame = found.orElseThrow();
        assertThat(foundGame.getTurnSide()).isEqualTo(game.getTurnSide());
        assertThat(foundGame.getStatus()).isEqualTo(game.getStatus());
        assertThat(foundGame.getBoard().pieces()).isEqualTo(game.getBoard().pieces());
    }

    @Test
    void 게임을_수정하면_변경된_보드와_턴이_반영된다() {
        // given
        JanggiGame game = JanggiGame.of(SangSetupType.LEFT_SANG_SETUP, SangSetupType.RIGHT_SANG_SETUP);
        Long gameId = repository.save(game);

        // when
        JanggiGame movedGame = game.move(new Position(3, 0), new Position(4, 0));
        repository.update(gameId, movedGame);
        JanggiGame foundGame = repository.findById(gameId).orElseThrow();

        // then
        assertThat(foundGame.getTurnSide()).isEqualTo(movedGame.getTurnSide());
        assertThat(foundGame.getStatus()).isEqualTo(movedGame.getStatus());
        assertThat(foundGame.getBoard().pieces()).isEqualTo(movedGame.getBoard().pieces());
    }

    @Test
    void 존재하지_않는_게임은_조회할_수_없다() {
        // when
        Optional<JanggiGame> found = repository.findById(99999L);
        // then
        assertThat(found).isEmpty();
    }

    private void clearDatabase() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM board_piece");
            statement.executeUpdate("DELETE FROM game");
        } catch (SQLException e) {
            throw new IllegalStateException("테스트 데이터 초기화에 실패했습니다.", e);
        }
    }
}