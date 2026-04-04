package db.repository;

import board.Board;
import core.GameStatus;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.model.BoardPiece;
import db.model.Game;
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
        Optional<Game> latestGame = gameDao.findLatest();
        if (latestGame.isEmpty()) {
            return Optional.empty();
        }

        Game gameRecord = latestGame.get();
        List<BoardPiece> boardPieceRecords = boardPieceDao.findByGameId(gameRecord.id());
        return Optional.of(toJanggiGame(gameRecord, boardPieceRecords));
    }

    @Override
    public void saveLatest(final JanggiGame janggiGame) {
        Optional<Game> latestGame = gameDao.findLatest();

        if (latestGame.isEmpty()) {
            Long gameId = gameDao.save(toGame(janggiGame));
            boardPieceDao.saveAll(gameId, toBoardPieces(janggiGame.getBoard()));
            return;
        }

        Long gameId = latestGame.get().id();
        gameDao.update(toGame(gameId, janggiGame));
        boardPieceDao.deleteByGameId(gameId);
        boardPieceDao.saveAll(gameId, toBoardPieces(janggiGame.getBoard()));
    }

    private Game toGame(final JanggiGame janggiGame) {
        return new Game(
            null,
            janggiGame.getTurnSide(),
            janggiGame.getStatus()
        );
    }

    private Game toGame(final Long gameId, final JanggiGame janggiGame) {
        return new Game(
            gameId,
            janggiGame.getTurnSide(),
            janggiGame.getStatus()
        );
    }

    private List<BoardPiece> toBoardPieces(final Board board) {
        return board.pieces().entrySet().stream()
            .map(this::toBoardPiece)
            .toList();
    }

    private BoardPiece toBoardPiece(final Map.Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        Piece piece = entry.getValue();

        return new BoardPiece(
            position.getRowValue(),
            position.getColumnValue(),
            piece.getType(),
            piece.getSide()
        );
    }

    private JanggiGame toJanggiGame(final Game gameRecord, final List<BoardPiece> boardPieceRecords) {
        Board board = toBoard(boardPieceRecords);
        Turn turn = Turn.from(gameRecord.turnSide());
        GameStatus status = gameRecord.status();
        return new JanggiGame(board, turn, status);
    }

    private Board toBoard(final List<BoardPiece> boardPieceRecords) {
        Map<Position, Piece> pieces = boardPieceRecords.stream()
            .collect(Collectors.toMap(
                this::toPosition,
                this::toPiece
            ));
        return new Board(pieces);
    }

    private Position toPosition(final BoardPiece boardPieceRecord) {
        return new Position(boardPieceRecord.row(), boardPieceRecord.column());
    }

    private Piece toPiece(final BoardPiece boardPieceRecord) {
        return new Piece(boardPieceRecord.pieceSide(), boardPieceRecord.pieceType());
    }
}
