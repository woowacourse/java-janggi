package janggi.repository;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.entity.BoardCellEntity;
import java.util.List;
import java.util.Optional;

public interface BoardCellRepository {

    long save(BoardCellEntity boardCellEntity);

    List<Long> saveAll(List<BoardCellEntity> boardCellEntities);

    Optional<BoardCellEntity> findByPositionAndGameId(Position position, long gameId);

    List<BoardCellEntity> findAllByGameId(long gameId);

    long upsertByPositionAndGameId(Position position, long gameId, Piece piece);

    void deleteByPositionAndGameId(Position position, long gameId);

}
