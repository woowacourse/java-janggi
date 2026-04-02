package janggi.repository;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardCellEntity;

public interface BoardCellRepository {

    long save(BoardCellEntity boardCellEntity);

    boolean existsByPosition(Position position);

    BoardCellEntity findById(long id);

    long updateByPosition(Position position, Piece piece);

}
