package janggi.repository.movement;

import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.util.ArrayList;
import java.util.List;

public class FakeMovementRepository implements MovementRepository {

    private final List<Movement> movements = new ArrayList<>();

    @Override
    public void save(Long gameId, Position from, Position to, PieceEntity capturedPiece) {
        movements.add(new Movement(
                gameId,
                from,
                to,
                capturedPiece == null ? null : capturedPiece.dynasty(),
                capturedPiece == null ? null : capturedPiece.type()
        ));
    }

    public List<Movement> findAllByGameId(Long gameId) {
        return movements.stream()
                .filter(movement -> movement.gameId().equals(gameId))
                .toList();
    }

    public record Movement(
            Long gameId,
            Position from,
            Position to,
            String destTeam,
            String destType
    ) {
    }

}
