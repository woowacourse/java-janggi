package db.dao;

import db.model.BoardPieceEntity;
import java.util.List;
import java.util.Optional;

public interface BoardPieceDao {

    Long save(BoardPieceEntity boardPiece);

    void saveAll(List<BoardPieceEntity> boardPieces);

    List<BoardPieceEntity> findAllByGameId(Long gameId);

    Optional<BoardPieceEntity> findByGameIdAndPosition(Long gameId, int row, int column);

    void updatePosition(Long id, int row, int column);

    void deleteByGameIdAndPosition(Long gameId, int row, int column);
}
