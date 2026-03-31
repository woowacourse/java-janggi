package repository;

import domain.place.piece.Side;
import entity.GameStateEntity;

public interface GameStateRepository {

    void save(int roomId, Side turn);

    GameStateEntity findByRoomId(int roomId);
}
