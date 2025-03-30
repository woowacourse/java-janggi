package janggi.entity;

import java.time.LocalDateTime;

public class GameRoomEntity {
    private Integer id;
    private String turnColor;
    private LocalDateTime startTime;
    private LocalDateTime lastUpdated;
    private boolean isFinished;
    private String winner;
    private LocalDateTime endTime;
    private int redScore;
    private int blueScore;

    public GameRoomEntity(Integer id, String turnColor, LocalDateTime startTime, LocalDateTime lastUpdated,
                          boolean isFinished, String winner, LocalDateTime endTime, int redScore, int blueScore) {
        this.id = id;
        this.turnColor = turnColor;
        this.startTime = startTime;
        this.lastUpdated = lastUpdated;
        this.isFinished = isFinished;
        this.winner = winner;
        this.endTime = endTime;
        this.redScore = redScore;
        this.blueScore = blueScore;
    }

    public static GameRoomEntity createNewGameRoom(String turnColor) {
        LocalDateTime now = LocalDateTime.now();
        return new GameRoomEntity(null, turnColor, now, now, false, null, null, 0, 0);
    }

    public static GameRoomEntity ofPlaying(int id, String turnColor,
                                           LocalDateTime startTime, LocalDateTime lastUpdated) {
        return new GameRoomEntity(id, turnColor, startTime, lastUpdated,
                false, null, null, 0, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTurnColor() {
        return turnColor;
    }

    public void setTurnColor(String turnColor) {
        this.turnColor = turnColor;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getRedScore() {
        return redScore;
    }

    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(int blueScore) {
        this.blueScore = blueScore;
    }
}
