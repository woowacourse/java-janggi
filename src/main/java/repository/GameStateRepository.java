package repository;

import domain.place.piece.Side;
import dto.GameStateDto;
import java.sql.Connection;

public interface GameStateRepository {

    void save(long roomId, Side turn, Connection conn);

    GameStateDto findByRoomId(long roomId, Connection conn);
}
