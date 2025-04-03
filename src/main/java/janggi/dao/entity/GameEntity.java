package janggi.dao.entity;

import janggi.GameState;
import janggi.piece.Team;

public class GameEntity {

    private Long id;
    private Team currentTeam;
    private GameState status;
    private double chuScore;
    private double hanScore;

    public GameEntity(final Long id, final Team currentTeam, final GameState status, final double chuScore,
                      final double hanScore) {
        this.id = id;
        this.currentTeam = currentTeam;
        this.status = status;
        this.chuScore = chuScore;
        this.hanScore = hanScore;
    }

    public Long getId() {
        return id;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void updateGameTurn(final Team currentTurnTeam) {
        this.currentTeam = currentTurnTeam;
    }

    public GameState getStatus() {
        return status;
    }

    public double getChuScore() {
        return chuScore;
    }

    public double getHanScore() {
        return hanScore;
    }
}
