package db.dao;

import db.model.BoardPiece;
import java.util.List;

public interface BoardPieceDao {

    void saveAll(Long gameId, List<BoardPiece> boardPieces);

    List<BoardPiece> findByGameId(Long gameId);

    void deleteByGameId(Long gameId);
}
