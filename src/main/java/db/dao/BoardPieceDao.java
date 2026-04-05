package db.dao;

import db.model.BoardPieceEntity;
import java.util.List;

public interface BoardPieceDao {

    void saveAll(Long gameEntityId, List<BoardPieceEntity> boardPieceEntities);

    List<BoardPieceEntity> findByGameId(Long gameEntityId);

    void deleteByGameId(Long gameEntityId);
}
