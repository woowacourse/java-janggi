package repository;

import domain.place.piece.Side;
import entity.GameStateEntity;
import java.sql.Connection;

public interface GameStateRepository {

    void save(long roomId, Side turn, Connection conn);

    GameStateEntity findByRoomId(long roomId);
}
