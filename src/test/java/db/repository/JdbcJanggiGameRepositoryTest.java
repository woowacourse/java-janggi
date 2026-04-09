package db.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import board.Board;
import core.GameStatus;
import service.GameSummary;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.dao.JdbcBoardPieceDao;
import db.dao.JdbcGameDao;
import db.dao.JdbcMoveHistoryDao;
import db.dao.MoveHistoryDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.FlywayDatabaseMigrator;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import core.MoveHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import core.Turn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

@Disabled(value = "외부 DB 의존성에 의한 테스트의 결과 비일관성 방지를 위해 비활성화 합니다.")
class JdbcJanggiGameRepositoryTest {

    private static final Board EMPTY_BOARD = new Board(Map.of());

    private ConnectionManager connectionManager;
    private JanggiGameRepository repository;

    @BeforeEach
    void setUp() {
        connectionManager = new TestConnectionManager();
        DatabaseMigrator migrator = new FlywayDatabaseMigrator(connectionManager);
        migrator.migrate();

        GameDao gameDao = new JdbcGameDao();
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao();
        MoveHistoryDao moveHistoryDao = new JdbcMoveHistoryDao();
        repository = new JdbcJanggiGameRepository(gameDao, boardPieceDao, moveHistoryDao);
    }

    @AfterEach
    void clear() {
        clearDatabase();
    }

    @Test
    void 게임을_저장할_수_있다() {
        // given
        JanggiGame game = new JanggiGame(EMPTY_BOARD, Turn.CHO_TURN, GameStatus.PLAYING);
        // when & then
        assertThatCode(() -> executeInTransaction(connection -> {
            repository.saveGame(connection, game);
            return null;
        })).doesNotThrowAnyException();
    }

    @Test
    void 저장된_게임을_ID로_다시_조회할_수_있다() {
        // given
        JanggiGame game = new JanggiGame(EMPTY_BOARD, Turn.CHO_TURN, GameStatus.PLAYING);
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        // when
        Optional<JanggiGame> found = executeReadOnly(connection -> repository.findGameById(connection, gameId));
        // then
        assertThat(found).isPresent();
        JanggiGame foundGame = found.orElseThrow();
        assertThat(foundGame.getTurnSide()).isEqualTo(game.getTurnSide());
        assertThat(foundGame.getStatus()).isEqualTo(game.getStatus());
        assertThat(foundGame.getBoard().pieces()).isEqualTo(game.getBoard().pieces());
    }

    @Test
    void 게임의_STATUS_를_수정하면_변경된_STATUS_가_반영된다() {
        // given
        Turn turn = Turn.CHO_TURN;
        GameStatus status = GameStatus.PLAYING;
        JanggiGame game = new JanggiGame(EMPTY_BOARD, turn, status);
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));

        JanggiGame updatedGame = new JanggiGame(EMPTY_BOARD, turn, GameStatus.HAN_WIN_BY_GUNG);
        // when
        executeInTransaction(connection -> {
            repository.updateGame(connection, gameId, updatedGame);
            return null;
        });
        // then
        JanggiGame foundGame = executeReadOnly(connection ->
            repository.findGameById(connection, gameId).orElseThrow()
        );
        assertThat(foundGame.getStatus()).isEqualTo(GameStatus.HAN_WIN_BY_GUNG);
    }

    @Test
    void 기존의_모든_게임을_최근_생성된_순서로_반환한다() {
        // given
        Turn turn = Turn.CHO_TURN;
        GameStatus status = GameStatus.PLAYING;
        JanggiGame firstGame = new JanggiGame(EMPTY_BOARD, turn, status);
        JanggiGame secondGame = new JanggiGame(EMPTY_BOARD, turn, status);

        Long firstGameId = executeInTransaction(connection -> repository.saveGame(connection, firstGame));
        Long secondGameId = executeInTransaction(connection -> repository.saveGame(connection, secondGame));
        // when
        List<GameSummary> foundGames = executeReadOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc
        );
        // then
        assertThat(foundGames).hasSize(2);
        assertThat(foundGames.getFirst().id()).isEqualTo(secondGameId);
        assertThat(foundGames.get(1).id()).isEqualTo(firstGameId);
    }

    @Test
    void 기물의_위치를_수정하면_변경된_위치가_반영된다() {
        // given
        Position beforePosition = new Position(0, 0);
        Position afterPosition = new Position(1, 0);
        Piece beforePositionPiece = new Piece(Side.CHO, PieceType.CHA);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(beforePosition, beforePositionPiece)),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        // when
        executeInTransaction(connection -> {
            repository.updatePiecePosition(connection, gameId, beforePosition, afterPosition);
            return null;
        });
        // then
        JanggiGame savedGame = executeReadOnly(connection ->
            repository.findGameById(connection, gameId).orElseThrow()
        );
        Piece savedAfterPositionPiece = savedGame.getBoard().pieces().get(afterPosition);
        assertThat(savedAfterPositionPiece).isEqualTo(beforePositionPiece);
    }

    @Test
    void 기물의_위치를_수정하면_기존_위치에는_기물이_존재하지_않는다() {
        // given
        Position beforePosition = new Position(0, 0);
        Position afterPosition = new Position(1, 0);
        Piece beforePositionPiece = new Piece(Side.CHO, PieceType.CHA);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(beforePosition, beforePositionPiece)),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        // when
        executeInTransaction(connection -> {
            repository.updatePiecePosition(connection, gameId, beforePosition, afterPosition);
            return null;
        });
        // then
        JanggiGame savedGame = executeReadOnly(connection ->
            repository.findGameById(connection, gameId).orElseThrow()
        );
        Piece savedBeforePositionPiece = savedGame.getBoard().pieces().get(beforePosition);
        assertThat(savedBeforePositionPiece).isNull();
    }

    @Test
    void 존재하지_않는_게임은_조회할_수_없다() {
        // when
        Optional<JanggiGame> found = executeReadOnly(connection -> repository.findGameById(connection, 1L));
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void 이동_기록을_저장할_때_move_order_를_1씩_늘리며_저장한다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece capturedPiece = new Piece(Side.HAN, PieceType.PO);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(
                departure, movingPiece,
                destination, capturedPiece
            )),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));

        MoveHistory firstMove = new MoveHistory(departure, destination, movingPiece, capturedPiece);
        MoveHistory secondMove = new MoveHistory(destination, new Position(2, 0), movingPiece, null);
        // when
        executeInTransaction(connection -> {
            repository.saveMoveHistory(connection, gameId, firstMove);
            repository.saveMoveHistory(connection, gameId, secondMove);
            return null;
        });
        // then
        List<db.model.MoveHistoryEntity> histories = executeReadOnly(connection ->
            new JdbcMoveHistoryDao().findAllByGameIdOrderByMoveOrderAsc(connection, gameId)
        );
        assertThat(histories).hasSize(2);
        assertThat(histories.get(0).moveOrder()).isEqualTo(1);
        assertThat(histories.get(1).moveOrder()).isEqualTo(2);
    }

    @Test
    void 이동_기록을_저장할_때_도착지에_기물이_있다면_공격_여부가_TRUE_로_저장된다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece capturedPiece = new Piece(Side.HAN, PieceType.PO);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(
                departure, movingPiece,
                destination, capturedPiece
            )),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        MoveHistory moveHistory = new MoveHistory(departure, destination, movingPiece, capturedPiece);
        // when
        executeInTransaction(connection -> {
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
        // then
        List<db.model.MoveHistoryEntity> histories = executeReadOnly(connection ->
            new JdbcMoveHistoryDao().findAllByGameIdOrderByMoveOrderAsc(connection, gameId)
        );
        db.model.MoveHistoryEntity saved = histories.getFirst();
        assertThat(saved.gameId()).isEqualTo(gameId);
        assertThat(saved.isCapture()).isTrue();
    }

    @Test
    void 이동_기록을_저장할_때_도착지에_기물이_없다면_공격_여부가_FALSE_로_저장된다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHA);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(departure, movingPiece)),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        MoveHistory moveHistory = new MoveHistory(departure, destination, movingPiece, null);
        // when
        executeInTransaction(connection -> {
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
        // then
        List<db.model.MoveHistoryEntity> histories = executeReadOnly(connection ->
            new JdbcMoveHistoryDao().findAllByGameIdOrderByMoveOrderAsc(connection, gameId)
        );
        db.model.MoveHistoryEntity saved = histories.getFirst();
        assertThat(saved.gameId()).isEqualTo(gameId);
        assertThat(saved.isCapture()).isFalse();
    }

    @Test
    void 이동_기록을_저장할_때_출발지_상태를_저장한다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece capturedPiece = new Piece(Side.HAN, PieceType.PO);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(
                departure, movingPiece,
                destination, capturedPiece
            )),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        MoveHistory moveHistory = new MoveHistory(departure, destination, movingPiece, capturedPiece);
        // when
        executeInTransaction(connection -> {
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
        // then
        List<db.model.MoveHistoryEntity> histories = executeReadOnly(connection ->
            new JdbcMoveHistoryDao().findAllByGameIdOrderByMoveOrderAsc(connection, gameId)
        );
        db.model.MoveHistoryEntity saved = histories.getFirst();
        assertThat(saved.gameId()).isEqualTo(gameId);
        assertThat(saved.movingPieceType()).isEqualTo(movingPiece.type());
        assertThat(saved.movingPieceSide()).isEqualTo(movingPiece.side());
        assertThat(saved.departureRow()).isEqualTo(departure.getRowIndex());
        assertThat(saved.departureColumn()).isEqualTo(departure.getColumnIndex());
    }

    @Test
    void 이동_기록을_저장할_때_도착지_상태를_저장한다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece capturedPiece = new Piece(Side.HAN, PieceType.PO);
        JanggiGame game = new JanggiGame(
            new Board(Map.of(
                departure, movingPiece,
                destination, capturedPiece
            )),
            Turn.CHO_TURN,
            GameStatus.PLAYING
        );
        Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        MoveHistory moveHistory = new MoveHistory(departure, destination, movingPiece, capturedPiece);
        // when
        executeInTransaction(connection -> {
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
        // then
        List<db.model.MoveHistoryEntity> histories = executeReadOnly(connection ->
            new JdbcMoveHistoryDao().findAllByGameIdOrderByMoveOrderAsc(connection, gameId)
        );
        db.model.MoveHistoryEntity saved = histories.getFirst();
        assertThat(saved.gameId()).isEqualTo(gameId);
        assertThat(saved.destinationRow()).isEqualTo(destination.getRowIndex());
        assertThat(saved.destinationColumn()).isEqualTo(destination.getColumnIndex());
        assertThat(saved.isCapture()).isTrue();
        assertThat(saved.capturedPieceType()).isEqualTo(capturedPiece.type());
        assertThat(saved.capturedPieceSide()).isEqualTo(capturedPiece.side());
    }

    private <T> T executeReadOnly(final SqlConnectionOperation<T> operation) {
        try (Connection connection = connectionManager.getConnection()) {
            return operation.execute(new SqlConnectionWrapper(connection));
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        } catch (Exception e) {
            throw new IllegalStateException("DB 조회에 실패했습니다.", e);
        }
    }

    private <T> T executeInTransaction(final SqlConnectionOperation<T> operation) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = operation.execute(new SqlConnectionWrapper(connection));
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalStateException("DB 작업에 실패했습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }

    @FunctionalInterface
    interface SqlConnectionOperation<T> {
        T execute(SqlConnection connection) throws Exception;
    }

    private void clearDatabase() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM move_history");
            statement.executeUpdate("DELETE FROM board_piece");
            statement.executeUpdate("DELETE FROM game");
        } catch (SQLException e) {
            throw new IllegalStateException("테스트 데이터 초기화에 실패했습니다.", e);
        }
    }
}
