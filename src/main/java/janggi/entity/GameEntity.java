package janggi.entity;

import java.sql.Date;

public class GameEntity {
    private int gameId;
    private String state;
    private String turn;
    private Date startDate;

    public GameEntity(int gameId, String state, String turn, Date startDate) {
        this.gameId = gameId;
        this.state = state;
        this.turn = turn;
        this.startDate = startDate;
    }

    public int getGameId() {
        return gameId;
    }

    public String getState() {
        return state;
    }

    public String getTurn() {
        return turn;
    }

    public Date getStartDate() {
        return startDate;
    }
}
