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

    Optional<BoardCellEntity> findByPosition(Position position);

    List<BoardCellEntity> findAllByGameId(long id);

    long upsertByPosition(long gameId, Position position, Piece piece);

    void deleteByPosition(long gameId, Position position);

}
