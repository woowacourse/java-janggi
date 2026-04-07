package janggi.repository.piece;

import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.util.List;

public interface PieceRepository {

    void saveAll(Long gameId, List<PieceEntity> pieces);

    void updatePiece(Long gameId, Position from, Position to);

    List<PieceEntity> findAllByGameId(Long gameId);

}
