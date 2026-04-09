package janggi.repository.movement;

import janggi.domain.position.Position;
import janggi.entity.PieceEntity;

public interface MovementRepository {

    void save(Long gameId, Position from, Position to, PieceEntity capturedPiece);

}
