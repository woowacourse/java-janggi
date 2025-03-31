package entity;

import dao.BoardDao;
import domain.board.BoardPoint;
import java.util.List;

public class BoardRepository {
    private final BoardDao boardDao;

    public BoardRepository(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<BoardEntity> getAllBoards() {
        return boardDao.getBoardEntities();
    }

    public BoardEntity findByBoardPoint(final BoardPoint boardPoint) {
        return boardDao.findByBoardPoint(boardPoint);
    }

    public void delete(BoardEntity boardEntity) {
        boardDao.delete(boardEntity);
    }

    public void save(BoardPoint arrivalBoardPoint, long pieceId) {
        boardDao.save(arrivalBoardPoint, pieceId);
    }

    public void updatePiece(BoardPoint arrivalBoardPoint, long pieceId) {
        boardDao.updatePiece(arrivalBoardPoint, pieceId);
    }
}
