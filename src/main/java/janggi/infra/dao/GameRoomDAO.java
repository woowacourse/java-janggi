package janggi.infra.dao;

import janggi.infra.entity.GameRoomEntity;

import java.util.Optional;

public interface GameRoomDAO {

    Long save(GameRoomEntity gameRoomEntity);

    Optional<GameRoomEntity> findById(Long id);

}
