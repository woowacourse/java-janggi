package janggi.repository.movement;

import janggi.domain.position.Position;

public interface MovementRepository {

    void save(Long gameId, Position from, Position to);

}
