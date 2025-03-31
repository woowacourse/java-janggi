package repository;

import java.util.List;

import dao.PieceDao;
import model.board.Board;
import model.piece.Piece;

public class BoardRepository {

    private final PieceDao pieceDao = new PieceDao();

    public void setUpTeam(int gameId, List<Piece> pieces) {
        pieceDao.insertAll(gameId, pieces);
    }

    public void save(int gameId, Board board) {
        pieceDao.updateAllByGameId(gameId, board.getPieces());
    }

    public Board findByGameId(int gameId) {
        List<Piece> pieces = pieceDao.selectAllByGameId(gameId);
        return new Board(pieces);
    }

    public void removeAllByGameId(int gameId) {
        pieceDao.deleteAllInGame(gameId);
    }
}
