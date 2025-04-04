package entity;

import dao.BoardDao;
import domain.board.BoardPoint;
import execptions.JanggiArgumentException;
import java.util.List;
import java.util.Optional;

public class BoardRepository {
    private final BoardDao boardDao;

    public BoardRepository(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<BoardEntity> getAllBoards() {
        return boardDao.getBoardEntities();
    }

    public BoardEntity findByBoardPoint(final BoardPoint boardPoint) {
        Optional<BoardEntity> boardEntity = boardDao.findByBoardPoint(boardPoint);
        if (boardEntity.isEmpty()) {
            throw new JanggiArgumentException("해당 보드 포인트 위에 기물이 존재하지 않습니다.");
        }
        return boardEntity.get();
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
