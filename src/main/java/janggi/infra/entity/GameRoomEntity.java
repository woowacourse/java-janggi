package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;

import java.time.LocalDateTime;

public class GameRoomEntity {

    private Long id;
    private String roomName;
    private Dynasty lastTurn;
    private LocalDateTime lastPlayedAt;
}
