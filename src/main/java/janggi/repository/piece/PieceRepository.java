package janggi.repository.piece;

import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.util.List;

public interface PieceRepository {

    void saveAll(Connection connection, Long gameId, List<PieceEntity> pieces);

    void update(Connection connection, Long gameId, Position from, Position to);

    List<PieceEntity> findAllByGameId(Long gameId);

}
