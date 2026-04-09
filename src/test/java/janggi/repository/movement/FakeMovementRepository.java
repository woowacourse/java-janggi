package janggi.repository.movement;

import janggi.domain.position.Position;
import janggi.entity.MovementEntity;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeMovementRepository implements MovementRepository {

    private long sequence = 1L;
    private final List<MovementEntity> movements = new ArrayList<>();

    @Override
    public void save(Connection connection, Long gameId, Position from, Position to, PieceEntity capturedPiece) {
        movements.add(new MovementEntity(
                sequence++,
                gameId,
                from,
                to,
                capturedPiece == null ? null : capturedPiece.dynasty(),
                capturedPiece == null ? null : capturedPiece.type()
        ));
    }

    @Override
    public Optional<MovementEntity> findLatestByGameId(Long gameId) {
        return movements.stream()
                .filter(movement -> movement.gameId().equals(gameId))
                .reduce((first, second) -> second);
    }

    @Override
    public void deleteById(Connection connection, Long movementId) {
        movements.removeIf(movement -> movement.id().equals(movementId));
    }

    public List<MovementEntity> findAllByGameId(Long gameId) {
        return movements.stream()
                .filter(movement -> movement.gameId().equals(gameId))
                .toList();
    }

}
