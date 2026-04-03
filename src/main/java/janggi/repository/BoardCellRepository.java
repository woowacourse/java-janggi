package janggi.repository;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardCellEntity;
import java.util.Optional;

public interface BoardCellRepository {

    long save(BoardCellEntity boardCellEntity);

    boolean existsByPosition(Position position);

    Optional<BoardCellEntity> findById(long id);

    long updateByPosition(Position position, Piece piece);

}
