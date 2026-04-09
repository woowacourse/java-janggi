package janggi.repository.movement;

import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class FakeMovementRepository implements MovementRepository {

    private final List<Movement> movements = new ArrayList<>();

    @Override
    public void save(Long gameId, Position from, Position to) {
        movements.add(new Movement(gameId, from, to));
    }

    public record Movement(
            Long gameId,
            Position from,
            Position to
    ) {
    }

}
