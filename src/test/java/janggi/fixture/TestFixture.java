package janggi.fixture;

import janggi.domain.dynasty.Dynasty;
import janggi.infra.entity.GameRoomEntity;

import java.time.LocalDateTime;

public class TestFixture {

    public static GameRoomEntity createGameRoomEntity() {
        return new GameRoomEntity("room1", Dynasty.HAN,
                LocalDateTime.of(2026, 4, 3, 15, 30));
    }
}
