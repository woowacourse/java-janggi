package repository;

import java.util.List;

import dao.GameDao;
import dao.PieceDao;
import model.board.Board;
import model.piece.Piece;

public class BoardRepository {

    private final GameDao gameDao = new GameDao();
    private final PieceDao pieceDao = new PieceDao();

    public void save(int gameId, Board board) {
        pieceDao.updateAllByGameId(gameId, board.getPieces());
    }

    public Board findById(int gameId) {
        List<Piece> pieces = pieceDao.selectAllByGameId(gameId);
        return new Board(pieces);
    }
}
