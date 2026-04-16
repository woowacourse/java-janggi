package janggi.dto;

import java.time.LocalDateTime;

public class GameRoomDto {
    private final int id;
    private final String currentTurn;
    private final String status;
    private final double choScore;
    private final double hanScore;
    private final LocalDateTime createdAt;

    public GameRoomDto(int id, String currentTurn, String status, double choScore, double hanScore, LocalDateTime createdAt) {
        this.id = id;
        this.currentTurn = currentTurn;
        this.status = status;
        this.choScore = choScore;
        this.hanScore = hanScore;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getStatus() {
        return status;
    }

    public double getChoScore() {
        return choScore;
    }

    public double getHanScore() {
        return hanScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
