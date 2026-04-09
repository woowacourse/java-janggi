package db.repository;

import board.Board;
import core.GameStatus;
import core.GameSummary;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.dao.MoveHistoryDao;
import db.jdbc.SqlConnection;
import db.model.BoardPieceEntity;
import db.model.GameEntity;
import db.model.MoveHistoryEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import movepolicy.MoveHistory;
import participant.Turn;
import pieces.Piece;
import position.Position;

public class JdbcJanggiGameRepository implements JanggiGameRepository {

    private final GameDao gameDao;
    private final BoardPieceDao boardPieceDao;
    private final MoveHistoryDao moveHistoryDao;

    public JdbcJanggiGameRepository(
        final GameDao gameDao,
        final BoardPieceDao boardPieceDao,
        final MoveHistoryDao moveHistoryDao
    ) {
        this.gameDao = gameDao;
        this.boardPieceDao = boardPieceDao;
        this.moveHistoryDao = moveHistoryDao;
    }

    @Override
    public Long saveGame(final SqlConnection connection, final JanggiGame game) {
        final Long gameId = gameDao.save(connection, parseGameEntity(game));
        boardPieceDao.saveAll(connection, parseBoardPieceEntities(gameId, game.getBoard()));
        return gameId;
    }

    @Override
    public Optional<JanggiGame> findGameById(final SqlConnection connection, final Long gameId) {
        return gameDao.findById(connection, gameId)
            .map(gameEntity -> parseGame(gameEntity, boardPieceDao.findAllByGameId(connection, gameId)));
    }

    @Override
    public List<GameSummary> findTop10GameRoomsOrderByCreatedAtDesc(final SqlConnection connection) {
        return gameDao.findTop10OrderByCreatedAtDesc(connection).stream()
            .map(this::parseGameSummary)
            .toList();
    }

    @Override
    public void updateGame(final SqlConnection connection, final Long gameId, final JanggiGame game) {
        gameDao.updateState(connection, gameId, game.getTurn(), game.getStatus());
    }

    @Override
    public void updatePiecePosition(
        final SqlConnection connection,
        final Long gameId,
        final Position departure,
        final Position destination
    ) {
        final BoardPieceEntity movingPiece = boardPieceDao.findByGameIdAndPosition(
                connection, gameId, departure.getRowIndex(), departure.getColumnIndex())
            .orElseThrow(() -> new IllegalArgumentException("이동할 말이 없습니다."));

        final Optional<BoardPieceEntity> destinationPiece = boardPieceDao.findByGameIdAndPosition(
            connection, gameId, destination.getRowIndex(), destination.getColumnIndex());

        destinationPiece.ifPresent(boardPieceEntity ->
            boardPieceDao.deleteById(connection, boardPieceEntity.id()));

        boardPieceDao.updatePosition(
            connection,
            movingPiece.id(),
            destination.getRowIndex(),
            destination.getColumnIndex()
        );
    }

    @Override
    public void saveMoveHistory(
        final SqlConnection connection,
        final Long gameId,
        final MoveHistory moveHistory
    ) {
        final int moveOrder = moveHistoryDao.findLastMoveOrderByGameId(connection, gameId)
            .orElse(0);

        moveHistoryDao.save(connection, parseMoveHistoryEntity(gameId, moveOrder + 1, moveHistory));
    }

    @Override
    public List<MoveHistory> findMoveHistoriesByGameId(final SqlConnection connection, final Long gameId) {
        return moveHistoryDao.findAllByGameIdOrderByMoveOrderAsc(connection, gameId).stream()
            .map(this::parseMoveHistory)
            .toList();
    }

    @Override
    public void undoLastMove(final SqlConnection connection, final Long gameId) {
        final MoveHistoryEntity moveHistory = moveHistoryDao.findLastByGameId(connection, gameId)
            .orElseThrow(() -> new IllegalStateException("최근 이동 기록이 없습니다."));

        final BoardPieceEntity movedPiece = boardPieceDao.findByGameIdAndPosition(
                connection, gameId, moveHistory.destinationRow(), moveHistory.destinationColumn())
            .orElseThrow(() -> new IllegalStateException("복원할 기물을 찾을 수 없습니다."));

        boardPieceDao.updatePosition(
            connection, movedPiece.id(), moveHistory.departureRow(), moveHistory.departureColumn());

        if (moveHistory.isCapture()) {
            boardPieceDao.save(connection, parseBoardPieceEntity(gameId,
                new Position(moveHistory.destinationRow(), moveHistory.destinationColumn()),
                new Piece(moveHistory.capturedPieceSide(), moveHistory.capturedPieceType()))
            );
        }

        moveHistoryDao.deleteById(connection, moveHistory.id());

        gameDao.updateState(connection, gameId, Turn.from(moveHistory.movingPieceSide()), GameStatus.PLAYING);
    }

    private MoveHistory parseMoveHistory(MoveHistoryEntity entity) {
        if (entity.isCapture()) {
            return new MoveHistory(
                new Position(entity.departureRow(), entity.departureColumn()),
                new Position(entity.destinationRow(), entity.destinationColumn()),
                new Piece(entity.movingPieceSide(), entity.movingPieceType()),
                new Piece(entity.capturedPieceSide(), entity.capturedPieceType())
            );
        }
        return new MoveHistory(
            new Position(entity.departureRow(), entity.departureColumn()),
            new Position(entity.destinationRow(), entity.destinationColumn()),
            new Piece(entity.movingPieceSide(), entity.movingPieceType()),
            null
        );
    }

    private MoveHistoryEntity parseMoveHistoryEntity(
        final Long gameId,
        final int moveOrder,
        final MoveHistory moveHistory
    ) {
        final Piece capturedPiece = moveHistory.capturedPiece();
        if (capturedPiece == null) {
            return new MoveHistoryEntity(
                null,
                gameId,
                moveOrder,
                moveHistory.movingPiece().type(),
                moveHistory.movingPiece().side(),
                moveHistory.departure().getRowIndex(),
                moveHistory.departure().getColumnIndex(),
                moveHistory.destination().getRowIndex(),
                moveHistory.destination().getColumnIndex(),
                false,
                null,
                null
            );
        }
        return new MoveHistoryEntity(
            null,
            gameId,
            moveOrder,
            moveHistory.movingPiece().type(),
            moveHistory.movingPiece().side(),
            moveHistory.departure().getRowIndex(),
            moveHistory.departure().getColumnIndex(),
            moveHistory.destination().getRowIndex(),
            moveHistory.destination().getColumnIndex(),
            true,
            capturedPiece.type(),
            capturedPiece.side()
        );
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
            .map(entry -> parseBoardPieceEntity(gameId, entry.getKey(), entry.getValue()))
            .toList();
    }

    private BoardPieceEntity parseBoardPieceEntity(final Long gameId, Position position, final Piece piece) {
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
        return new JanggiGame(
            parseBoard(boardPieceEntities),
            gameEntity.turn(),
            gameEntity.status()
        );
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

    private Piece parsePiece(final BoardPieceEntity boardPieceEntity) {
        return new Piece(boardPieceEntity.pieceSide(), boardPieceEntity.pieceType());
    }
}
