package team.janggi.domain;

public class Game {
    private final Long id;
    private Team currentTurn;

    public Game(Team currentTurn) {
        this(null, currentTurn);
    }

    public Game(Long id, Team currentTurn) {
        this.id = id;
        this.currentTurn = currentTurn;
    }

    public void changeTurn() {
        if (this.currentTurn == Team.CHO) {
            this.currentTurn = Team.HAN;
            return;
        }

        this.currentTurn = Team.CHO;
    }

    public Long getId() {
        return id;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }

}