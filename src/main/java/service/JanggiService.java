package service;

import janggi.dao.PiecesDao;
import janggi.dao.TurnDao;
import janggi.domain.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.sql.SQLException;
import java.util.Map;

public class JanggiService {

    private final PiecesDao piecesDao;
    private final TurnDao turnDao;

    public JanggiService(PiecesDao piecesDao, TurnDao turnDao) {
        this.piecesDao = piecesDao;
        this.turnDao = turnDao;
    }

    public Board loadBoard() throws SQLException {
        Map<Position, Piece> savedPieces = piecesDao.loadPieces();
        if (savedPieces.isEmpty()) {
            throw new IllegalArgumentException("저장된 게임이 없습니다");
        }
        return new Board(savedPieces, turnDao.loadTurn());
    }

    public void saveBoard(Board board) throws SQLException {
        piecesDao.deletePieces();
        turnDao.deleteTurn();
        piecesDao.savePieces(board.getPieces());
        turnDao.saveTurn(board.getTurn());
    }
}
