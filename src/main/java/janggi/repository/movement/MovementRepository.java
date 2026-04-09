package janggi.repository.movement;

import janggi.domain.position.Position;
import janggi.entity.MovementEntity;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.util.Optional;

public interface MovementRepository {

    void save(Connection connection, Long gameId, Position from, Position to, PieceEntity capturedPiece);

    Optional<MovementEntity> findLatestByGameId(Long gameId);

    void deleteById(Connection connection, Long movementId);

}
