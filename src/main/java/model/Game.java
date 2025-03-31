package model;

public class Game {

    private Integer id;
    private Team turn;

    public Game(Team turn) {
        this.turn = turn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Team getTurn() {
        return turn;
    }

    public void nextTurn() {
        turn = turn.nextTurn();
    }
}
