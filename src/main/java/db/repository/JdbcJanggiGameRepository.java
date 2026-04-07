package db.repository;

import board.Board;
import core.GameStatus;
import core.GameSummary;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ConnectionManager;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import db.model.BoardPieceEntity;
import db.model.GameEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import participant.Turn;
import pieces.Piece;
import position.Position;

public class JdbcJanggiGameRepository implements JanggiGameRepository {

    private final GameDao gameDao;
    private final BoardPieceDao boardPieceDao;
    private final ConnectionManager connectionManager;

    public JdbcJanggiGameRepository(final GameDao gameDao, final BoardPieceDao boardPieceDao,
                                    final ConnectionManager connectionManager) {
        this.gameDao = gameDao;
        this.boardPieceDao = boardPieceDao;
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(final JanggiGame game) {
        return executeInTransaction(connection -> {
            final Long gameId = gameDao.save(connection, parseGameEntity(game));
            boardPieceDao.saveAll(connection, parseBoardPieceEntities(gameId, game.getBoard()));
            return gameId;
        });
    }

    @Override
    public Optional<JanggiGame> findById(final Long gameId) {
        return execute(connection -> gameDao.findById(connection, gameId)
            .map(gameEntity -> parseGame(gameEntity, boardPieceDao.findAllByGameId(connection, gameId)))
        );
    }

    @Override
    public List<GameSummary> findTop10GameRoomsOrderByCreatedAtDesc() {
        return execute(connection -> gameDao.findTop10OrderByCreatedAtDesc(connection).stream()
            .map(this::parseGameSummary)
            .toList());
    }

    @Override
    public void updateGameState(final Long gameId, final Turn turn, final GameStatus status) {
        executeInTransaction(connection -> {
            gameDao.updateState(connection, gameId, turn, status);
            return null;
        });
    }

    @Override
    public void updatePiecePosition(final Long gameId, final Position departure, final Position destination) {
        executeInTransaction(connection -> {
            final BoardPieceEntity movingPiece = boardPieceDao.findByGameIdAndPosition(
                    connection, gameId, departure.getRowIndex(), departure.getColumnIndex())
                .orElseThrow(() -> new IllegalArgumentException("이동할 말이 없습니다."));
            final Optional<BoardPieceEntity> destinationPiece = boardPieceDao.findByGameIdAndPosition(
                connection, gameId, destination.getRowIndex(), destination.getColumnIndex());

            if (destinationPiece.isPresent()) {
                boardPieceDao.deleteByGameIdAndPosition(
                    connection, gameId, destination.getRowIndex(), destination.getColumnIndex());
            }
            boardPieceDao.updatePosition(
                connection, movingPiece.id(), destination.getRowIndex(), destination.getColumnIndex());

            return null;
        });
    }

    private GameSummary parseGameSummary(final GameEntity gameEntity) {
        return new GameSummary(
            gameEntity.id(),
            gameEntity.turn(),
            gameEntity.status()
        );
    }

    private GameEntity parseGameEntity(final JanggiGame game) {
        return new GameEntity(
            null,
            game.getTurn(),
            game.getStatus()
        );
    }

    private List<BoardPieceEntity> parseBoardPieceEntities(final Long gameId, final Board board) {
        return board.pieces().entrySet().stream()
            .map(positionPieceEntry -> parseBoardEntity(gameId, positionPieceEntry))
            .toList();
    }

    private BoardPieceEntity parseBoardEntity(final Long gameId, final Map.Entry<Position, Piece> entry) {
        final Position position = entry.getKey();
        final Piece piece = entry.getValue();

        return new BoardPieceEntity(
            null,
            gameId,
            position.getRowIndex(),
            position.getColumnIndex(),
            piece.type(),
            piece.side()
        );
    }

    private JanggiGame parseGame(final GameEntity gameEntity, final List<BoardPieceEntity> boardPieceEntities) {
        final Board board = parseBoard(boardPieceEntities);
        final Turn turn = gameEntity.turn();
        final GameStatus status = gameEntity.status();
        return new JanggiGame(board, turn, status);
    }

    private Board parseBoard(final List<BoardPieceEntity> boardPieceEntities) {
        final Map<Position, Piece> pieces = boardPieceEntities.stream()
            .collect(Collectors.toMap(
                this::parsePosition,
                this::parsePiece
            ));
        return new Board(pieces);
    }

    private Position parsePosition(final BoardPieceEntity boardPieceEntity) {
        return new Position(boardPieceEntity.boardRow(), boardPieceEntity.boardColumn());
    }

    private Piece parsePiece(final BoardPieceEntity boardPieceEntityRecord) {
        return new Piece(boardPieceEntityRecord.pieceSide(), boardPieceEntityRecord.pieceType());
    }

    private <T> T execute(final ThrowingFunction<SqlConnection, T> operation) {
        try (Connection connection = connectionManager.getConnection()) {
            return operation.apply(new SqlConnectionWrapper(connection));
        } catch (Exception e) {
            throw new IllegalArgumentException("DB 조회에 실패했습니다.", e);
        }
    }

    private <T> T executeInTransaction(final ThrowingFunction<SqlConnection, T> operation) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                final T result = operation.apply(new SqlConnectionWrapper(connection));
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalArgumentException("DB 작업에 실패했습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("DB 연결에 실패했습니다.", e);
        }
    }

    @FunctionalInterface
    interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }
}
