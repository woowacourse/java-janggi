package janggi.dto;

import janggi.domain.team.Team;

import java.time.LocalDateTime;

public class GameInfo {

    private final long id;
    private final LocalDateTime updatedAt;
    private final double hanScore;
    private final double choScore;
    private final Team currentTeam;
    private final Team winner;

    public GameInfo(long id, LocalDateTime updatedAt, double hanScore, double choScore, Team currentTeam, Team winner) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.hanScore = hanScore;
        this.choScore = choScore;
        this.currentTeam = currentTeam;
        this.winner = winner;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public double getHanScore() {
        return hanScore;
    }

    public double getChoScore() {
        return choScore;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public Team getWinner() {
        return winner;
    }
}
