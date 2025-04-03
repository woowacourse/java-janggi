package janggi.dao.piece;

import janggi.dao.entity.PieceEntity;
import janggi.position.Position;
import java.util.List;

public interface PieceDao {

    void addPieces(final List<PieceEntity> pieceEntities);

    List<PieceEntity> findPiecesById(final Long gameId);

    void removePieceByPosition(final Long gameId, final Position position);

    void updatePiece(final PieceEntity pieceEntity);

    void deletePiecesBy(final Long gameId);
}
