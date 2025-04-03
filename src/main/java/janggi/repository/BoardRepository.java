package janggi.repository;

import janggi.dao.BoardDao;
import janggi.dao.PieceDao;
import janggi.domain.Board;
import janggi.domain.Position;
import janggi.entity.BoardEntity;
import janggi.entity.PieceEntity;
import java.util.Optional;

public class BoardRepository {

    private final BoardDao boardDao;
    private final PieceDao pieceDao;

    public BoardRepository(final BoardDao boardDao, final PieceDao pieceDao) {
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
    }

    public void saveBoard(BoardEntity boardEntity) {
        boardDao.save(boardEntity);
    }

    public void savePiece(PieceEntity pieceEntity, Position targetPosition) {
        Optional<PieceEntity> pieceEntityOptional = pieceDao.findByBoardIdAndRowAndColumn(pieceEntity.boardId(),
                targetPosition.getRow(),
                targetPosition.getColumn());
        if (pieceEntityOptional.isPresent()) {
            pieceDao.save(pieceEntity.addPieceId(pieceEntityOptional.get().pieceId()));
            return;
        }
        pieceDao.save(pieceEntity);
    }

    public void savePieceAll(final long boardId, final Board board) {
        board.getPositionToPiece()
                .forEach((position, piece) -> savePiece(PieceEntity.of(boardId, piece, position, true), position));
    }

    public Optional<Long> findByJanggiId(final long janggiId) {
        return boardDao.findByJanggiId(janggiId);
    }
}
