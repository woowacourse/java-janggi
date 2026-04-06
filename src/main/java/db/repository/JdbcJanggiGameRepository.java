package db.repository;

import board.Board;
import core.GameStatus;
import core.GameSummary;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.model.BoardPieceEntity;
import db.model.GameEntity;
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

    public JdbcJanggiGameRepository(final GameDao gameDao, final BoardPieceDao boardPieceDao) {
        this.gameDao = gameDao;
        this.boardPieceDao = boardPieceDao;
    }

    @Override
    public Long save(JanggiGame game) {
        Long gameId = gameDao.save(parseGameEntity(game));
        boardPieceDao.saveAll(parseBoardPieceEntities(gameId, game.getBoard()));
        return gameId;
    }

    @Override
    public Optional<JanggiGame> findById(Long gameId) {
        return gameDao.findById(gameId)
            .map(gameEntity -> parseGame(gameEntity, boardPieceDao.findAllByGameId(gameId)));
    }

    @Override
    public List<GameSummary> findTop10GameRoomsOrderByCreatedAtAsc() {
        return gameDao.findTop10OrderByCreatedAtAsc().stream()
            .map(gameEntity -> parseGameSummary(gameEntity))
            .toList();
    }

    @Override
    public void updateGameState(Long gameId, Turn turn, GameStatus status) {
        gameDao.updateState(gameId, turn, status);
    }

    @Override
    public void updatePiecePosition(Long gameId, Position departure, Position destination) {
        Optional<BoardPieceEntity> movingPiece = boardPieceDao.findByGameIdAndPosition(
            gameId, departure.getRowIndex(), departure.getColumnIndex());
        Optional<BoardPieceEntity> destinationPiece = boardPieceDao.findByGameIdAndPosition(
            gameId, destination.getRowIndex(), destination.getColumnIndex());

        if (destinationPiece.isPresent()) {
            boardPieceDao.deleteByGameIdAndPosition(gameId, destination.getRowIndex(), destination.getColumnIndex());
        }
        boardPieceDao.updatePosition(movingPiece.get().id(), destination.getRowIndex(), destination.getColumnIndex());
    }

    private GameSummary parseGameSummary(GameEntity gameEntity) {
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
        Position position = entry.getKey();
        Piece piece = entry.getValue();

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
        Board board = parseBoard(boardPieceEntities);
        Turn turn = gameEntity.turn();
        GameStatus status = gameEntity.status();
        return new JanggiGame(board, turn, status);
    }

    private Board parseBoard(final List<BoardPieceEntity> boardPieceEntities) {
        Map<Position, Piece> pieces = boardPieceEntities.stream()
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
}
