package janggi.repository;

import janggi.dao.BoardDao;
import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardEntity;

public class BoardRepositoryImpl implements BoardRepository {

    private final BoardDao boardDao;

    public BoardRepositoryImpl(final BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void save(final long janggiId,
                     final Position departure,
                     final Position destination,
                     final Piece piece,
                     boolean isAlive) {
        boardDao.save(new BoardEntity(0, janggiId, piece.getPieceType().name(), piece.getTeam().name(),
                destination.getRow(), destination.getColumn(), isAlive), departure);
    }

    @Override
    public void saveAll(final long janggiId, final Board board) {
        board.getPositionToPiece().forEach((key, value) -> save(janggiId, key, key, value, true));
    }
}
