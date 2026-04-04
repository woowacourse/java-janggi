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
    public Long save(final JanggiGame janggiGame) {
        Long gameId = gameDao.save(toGame(janggiGame));
        boardPieceDao.saveAll(gameId, toBoardPieces(janggiGame.getBoard()));
        return gameId;
    }

    @Override
    public void update(final Long gameId, final JanggiGame janggiGame) {
        validateGameId(gameId);

        gameDao.update(toGame(gameId, janggiGame));
        boardPieceDao.deleteByGameId(gameId);
        boardPieceDao.saveAll(gameId, toBoardPieces(janggiGame.getBoard()));
    }

    @Override
    public Optional<JanggiGame> findById(final Long gameId) {
        validateGameId(gameId);

        Optional<Game> gameRecord = gameDao.findById(gameId);
        if (gameRecord.isEmpty()) {
            return Optional.empty();
        }

        List<BoardPiece> boardPieceRecords = boardPieceDao.findByGameId(gameId);
        return Optional.of(toJanggiGame(gameRecord.get(), boardPieceRecords));
    }

    private void validateGameId(final Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("게임 ID가 필요합니다.");
        }
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

    private JanggiGame toJanggiGame(final Game game, final List<BoardPiece> boardPieceRecords) {
        Board board = toBoard(boardPieceRecords);
        Turn turn = Turn.from(game.turnSide());
        GameStatus status = game.status();

        return new JanggiGame(board, turn, status);
    }

    private Board toBoard(final List<BoardPiece> boardPieceRecords) {
        Map<Position, Piece> pieces = boardPieceRecords.stream()
            .collect(java.util.stream.Collectors.toMap(
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
