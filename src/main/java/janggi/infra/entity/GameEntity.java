package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;

import java.time.LocalDateTime;

public class GameEntity {

    private Long id;
    private RoomName roomName;
    private Dynasty currentTurn;
    private LocalDateTime lastPlayedAt;

    public GameEntity(Long id, RoomName roomName, Dynasty currentTurn, LocalDateTime lastPlayedAt) {
        this.id = id;
        this.roomName = roomName;
        this.currentTurn = currentTurn;
        this.lastPlayedAt = lastPlayedAt;
    }

    public GameEntity(RoomName roomName, Dynasty currentTurn, LocalDateTime lastPlayedAt) {
        this.roomName = roomName;
        this.currentTurn = currentTurn;
        this.lastPlayedAt = lastPlayedAt;
    }

    public Long id() {
        return id;
    }

    public RoomName roomName() {
        return roomName;
    }

    public Dynasty currentTurn() {
        return currentTurn;
    }

    public LocalDateTime lastPlayedAt() {
        return lastPlayedAt;
    }

    public void bindId(Long id) {
        if(this.id == null) {
            this.id = id;
        }
    }
}
