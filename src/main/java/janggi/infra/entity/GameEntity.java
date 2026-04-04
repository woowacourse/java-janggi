package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;

import java.time.LocalDateTime;

public class GameEntity {

    private Long id;
    private String roomName;
    private Dynasty lastTurn;
    private LocalDateTime lastPlayedAt;

    public GameEntity(Long id, String roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
        this.id = id;
        this.roomName = roomName;
        this.lastTurn = lastTurn;
        this.lastPlayedAt = lastPlayedAt;
    }

    public GameEntity(String roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
        this.roomName = roomName;
        this.lastTurn = lastTurn;
        this.lastPlayedAt = lastPlayedAt;
    }

    public Long id() {
        return id;
    }

    public String roomName() {
        return roomName;
    }

    public Dynasty lastTurn() {
        return lastTurn;
    }

    public LocalDateTime lastPlayedAt() {
        return lastPlayedAt;
    }

    public void bindId(Long id) {
        if(this.id != null) {
            this.id = id;
        }
    }
}
