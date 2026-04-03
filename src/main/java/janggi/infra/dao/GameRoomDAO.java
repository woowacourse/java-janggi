package janggi.infra.dao;

import janggi.infra.entity.GameRoomEntity;

import java.util.Optional;

public interface GameRoomDAO {

    void save(GameRoomEntity gameRoomEntity);

    Optional<GameRoomEntity> findById(Long id);

}
