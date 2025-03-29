package service;

import dao.BoardDao;
import domain.board.Position;
import domain.piece.Piece;
import java.util.Map;

public class BoardService {

    private final BoardDao boardDao;

    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void saveBoard(Map<Position, Piece> board) {
        boardDao.saveBoard(board);
    }

    public Map<Position, Piece> loadBoard() {
        return boardDao.loadBoard();
    }

    public void updatePosition(Position position, Position destination, Piece piece) {
        boardDao.updatePosition(position, destination, piece);
    }
}
