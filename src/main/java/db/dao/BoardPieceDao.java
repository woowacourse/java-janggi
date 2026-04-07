package db.dao;

import db.model.BoardPieceEntity;
import java.util.List;
import java.util.Optional;

public interface BoardPieceDao {

    void saveAll(final List<BoardPieceEntity> boardPieces);

    List<BoardPieceEntity> findAllByGameId(final Long gameId);

    Optional<BoardPieceEntity> findByGameIdAndPosition(final Long gameId, final int row, final int column);

    void updatePosition(final Long id, final int row, final int column);

    void deleteByGameIdAndPosition(final Long gameId, final int row, final int column);
}
