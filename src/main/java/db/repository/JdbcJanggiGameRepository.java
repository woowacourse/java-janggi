package db.repository;

import board.Board;
import core.GameStatus;
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
    public Optional<JanggiGame> findLatest() {
        Optional<GameEntity> latestGame = gameDao.findLatest();
        if (latestGame.isEmpty()) {
            return Optional.empty();
        }

        GameEntity gameEntity = latestGame.get();
        List<BoardPieceEntity> boardPieceEntities = boardPieceDao.findByGameId(gameEntity.id());
        return Optional.of(parseGame(gameEntity, boardPieceEntities));
    }

    @Override
    public void saveLatest(final JanggiGame janggiGame) {
        Optional<GameEntity> latestGame = gameDao.findLatest();

        if (latestGame.isEmpty()) {
            Long gameId = gameDao.save(parseGameEntity(janggiGame));
            boardPieceDao.saveAll(gameId, parseBoardPieceEntities(janggiGame.getBoard()));
            return;
        }

        Long gameId = latestGame.get().id();
        gameDao.update(parseGameEntity(gameId, janggiGame));
        boardPieceDao.deleteByGameId(gameId);
        boardPieceDao.saveAll(gameId, parseBoardPieceEntities(janggiGame.getBoard()));
    }

    private GameEntity parseGameEntity(final JanggiGame janggiGame) {
        return new GameEntity(
            null,
            janggiGame.getTurnSide(),
            janggiGame.getStatus()
        );
    }

    private GameEntity parseGameEntity(final Long gameId, final JanggiGame janggiGame) {
        return new GameEntity(
            gameId,
            janggiGame.getTurnSide(),
            janggiGame.getStatus()
        );
    }

    private List<BoardPieceEntity> parseBoardPieceEntities(final Board board) {
        return board.pieces().entrySet().stream()
            .map(this::parseBoardEntity)
            .toList();
    }

    private BoardPieceEntity parseBoardEntity(final Map.Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        Piece piece = entry.getValue();

        return new BoardPieceEntity(
            position.getRowIndex(),
            position.getColumnIndex(),
            piece.type(),
            piece.side()
        );
    }

    private JanggiGame parseGame(final GameEntity gameEntityRecord, final List<BoardPieceEntity> boardPieceEntityRecords) {
        Board board = parseBoard(boardPieceEntityRecords);
        Turn turn = Turn.from(gameEntityRecord.turnSide());
        GameStatus status = gameEntityRecord.status();
        return new JanggiGame(board, turn, status);
    }

    private Board parseBoard(final List<BoardPieceEntity> boardPieceEntityRecords) {
        Map<Position, Piece> pieces = boardPieceEntityRecords.stream()
            .collect(Collectors.toMap(
                this::parsePosition,
                this::parsePiece
            ));
        return new Board(pieces);
    }

    private Position parsePosition(final BoardPieceEntity boardPieceEntityRecord) {
        return new Position(boardPieceEntityRecord.row(), boardPieceEntityRecord.column());
    }

    private Piece parsePiece(final BoardPieceEntity boardPieceEntityRecord) {
        return new Piece(boardPieceEntityRecord.pieceSide(), boardPieceEntityRecord.pieceType());
    }
}
