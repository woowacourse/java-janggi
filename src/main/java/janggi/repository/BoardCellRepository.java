package janggi.repository;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardCellEntity;
import java.util.List;
import java.util.Optional;

public interface BoardCellRepository {

    long save(BoardCellEntity boardCellEntity);

    List<Long> saveAll(List<BoardCellEntity> boardCellEntities);

    boolean existsByPosition(Position position);

    Optional<BoardCellEntity> findById(long id);

    List<BoardCellEntity> findAllByBoardId(long id);

    long updateByPosition(Position position, Piece piece);

}
