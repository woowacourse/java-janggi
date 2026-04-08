package db.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import board.Board;
import core.GameStatus;
import core.GameSummary;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.FlywayDatabaseMigrator;
import db.jdbc.JdbcBoardPieceDao;
import db.jdbc.JdbcGameDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import participant.Turn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class JdbcJanggiGameRepositoryTest {

    private ConnectionManager connectionManager;
    private JanggiGameRepository repository;

    @BeforeEach
    void setUp() {
        connectionManager = new TestConnectionManager();
        DatabaseMigrator migrator = new FlywayDatabaseMigrator(connectionManager);
        migrator.migrate();

        GameDao gameDao = new JdbcGameDao();
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao();
        repository = new JdbcJanggiGameRepository(gameDao, boardPieceDao, connectionManager);
    }

    @AfterEach
    void clear() {
        clearDatabase();
    }

    @Test
    void 게임을_저장할_수_있다() {
        // given
        JanggiGame game = new JanggiGame(new Board(Map.of()), Turn.CHO_TURN, GameStatus.PLAYING);
        // when & then
        assertThatCode(() -> repository.save(game))
            .doesNotThrowAnyException();
    }

    @Test
    void 저장된_게임을_ID로_다시_조회할_수_있다() {
        // given
        JanggiGame game = new JanggiGame(new Board(Map.of()), Turn.CHO_TURN, GameStatus.PLAYING);
        Long gameId = repository.save(game);
        // when
        Optional<JanggiGame> found = repository.findById(gameId);
        // then
        assertThat(found).isPresent();
        JanggiGame foundGame = found.orElseThrow();
        assertThat(foundGame.getTurnSide()).isEqualTo(game.getTurnSide());
        assertThat(foundGame.getStatus()).isEqualTo(game.getStatus());
        assertThat(foundGame.getBoard().pieces()).isEqualTo(game.getBoard().pieces());
    }

    @Test
    void 게임의_턴을_수정하면_변경된_턴이_반영된다() {
        // given
        Turn turn = Turn.CHO_TURN;
        GameStatus status = GameStatus.PLAYING;
        JanggiGame game = new JanggiGame(new Board(Map.of()), turn, status);
        Long gameId = repository.save(game);
        // when
        repository.updateGameState(gameId, turn.other(), status);
        // then
        JanggiGame foundGame = repository.findById(gameId).orElseThrow();
        assertThat(foundGame.getTurn()).isEqualTo(turn.other());
    }

    @Test
    void 게임의_STATUS_를_수정하면_변경된_STATUS_가_반영된다() {
        // given
        Turn turn = Turn.CHO_TURN;
        GameStatus status = GameStatus.PLAYING;
        JanggiGame game = new JanggiGame(new Board(Map.of()), turn, status);
        Long gameId = repository.save(game);
        // when
        repository.updateGameState(gameId, turn.other(), GameStatus.CHO_WIN_BY_GUNG);
        // then
        JanggiGame foundGame = repository.findById(gameId).orElseThrow();
        assertThat(foundGame.getStatus()).isEqualTo(GameStatus.CHO_WIN_BY_GUNG);
    }

    @Test
    void 기존의_모든_게임을_최근_생성된_순서로_반환한다() {
        // given
        Turn turn = Turn.CHO_TURN;
        GameStatus status = GameStatus.PLAYING;
        JanggiGame firstGame = new JanggiGame(new Board(Map.of()), turn, status);
        JanggiGame secondGame = new JanggiGame(new Board(Map.of()), turn, status);
        Long firstGameId = repository.save(firstGame);
        Long secondGameId = repository.save(secondGame);
        // when
        List<GameSummary> foundGames = repository.findTop10GameRoomsOrderByCreatedAtDesc();
        // then
        assertThat(foundGames.getFirst().id()).isEqualTo(secondGameId);
    }

    @Test
    void 기물의_위치를_수정하면_변경된_위치가_반영된다() {
        // given
        Position beforePosition = new Position(0, 0);
        Position afterPosition = new Position(1, 0);
        Piece beforePositionPiece = new Piece(Side.CHO, PieceType.CHA);
        JanggiGame game = new JanggiGame(new Board(Map.of(
            beforePosition, beforePositionPiece)),
            Turn.CHO_TURN,
            GameStatus.PLAYING);
        Long gameId = repository.save(game);
        // when
        repository.updatePiecePosition(gameId, beforePosition, afterPosition);
        // then
        JanggiGame savedGame = repository.findById(gameId).orElseThrow();
        Piece savedAfterPositionPiece = savedGame.getBoard().pieces().get(afterPosition);
        assertThat(savedAfterPositionPiece).isEqualTo(beforePositionPiece);
    }

    @Test
    void 기물의_위치를_수정하면_기존_위치에는_기물이_존재하지_않는다() {
        // given
        Position beforePosition = new Position(0, 0);
        Position afterPosition = new Position(1, 0);
        Piece beforePositionPiece = new Piece(Side.CHO, PieceType.CHA);
        JanggiGame game = new JanggiGame(new Board(Map.of(
            beforePosition, beforePositionPiece)),
            Turn.CHO_TURN,
            GameStatus.PLAYING);
        Long gameId = repository.save(game);
        // when
        repository.updatePiecePosition(gameId, beforePosition, afterPosition);
        // then
        JanggiGame savedGame = repository.findById(gameId).orElseThrow();
        Piece savedBeforePositionPiece = savedGame.getBoard().pieces().get(beforePosition);
        assertThat(savedBeforePositionPiece).isEqualTo(null);
    }

    @Test
    void 존재하지_않는_게임은_조회할_수_없다() {
        // when
        Optional<JanggiGame> found = repository.findById(1L);
        // then
        assertThat(found).isEmpty();
    }

    private void clearDatabase() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM migration");
            statement.executeUpdate("DELETE FROM move_history");
            statement.executeUpdate("DELETE FROM board_piece");
            statement.executeUpdate("DELETE FROM game");
        } catch (SQLException e) {
            throw new IllegalStateException("테스트 데이터 초기화에 실패했습니다.", e);
        }
    }
}