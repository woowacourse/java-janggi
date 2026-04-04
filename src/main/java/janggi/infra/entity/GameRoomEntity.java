package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;

import java.time.LocalDateTime;

public class GameRoomEntity {

    private Long id;
    private String roomName;
    private Dynasty lastTurn;
    private LocalDateTime lastPlayedAt;

    public GameRoomEntity(Long id, String roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
        this.id = id;
        this.roomName = roomName;
        this.lastTurn = lastTurn;
        this.lastPlayedAt = lastPlayedAt;
    }

    public GameRoomEntity(String roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
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
}
