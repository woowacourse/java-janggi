package janggi.dao;

import janggi.dao.entity.PieceEntity;
import janggi.domain.piece.Point;
import java.util.List;

public interface PieceDao {
    void addPieces(List<PieceEntity> pieces);

    List<PieceEntity> findAllByGameId(Long gameId);

    void updatePiece(Long gameId, Point from, Point to);

    void deletePiece(Long gameId, Point point);
}
