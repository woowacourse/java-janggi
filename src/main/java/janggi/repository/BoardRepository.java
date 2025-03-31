package janggi.repository;

import janggi.dao.BoardDao;
import janggi.domain.Board;
import janggi.domain.Position;
import janggi.entity.BoardEntity;
import java.util.Optional;

public class BoardRepository {

    private final BoardDao boardDao;

    public BoardRepository(final BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void save(BoardEntity boardEntity, Position targetPosition) {
        Optional<BoardEntity> boardEntityOptional = boardDao.findByJanggiIdAndRowAndColumn(boardEntity.janggiId(),
                targetPosition.getRow(),
                targetPosition.getColumn());
        if (boardEntityOptional.isPresent()) {
            boardDao.save(boardEntity.addBoardId(boardEntityOptional.get().boardId()));
            return;
        }
        boardDao.save(boardEntity);
    }

    public void saveAll(final long janggiId, final Board board) {
        board.getPositionToPiece()
                .forEach((position, piece) -> save(BoardEntity.of(janggiId, piece, position, true), position));
    }
}
