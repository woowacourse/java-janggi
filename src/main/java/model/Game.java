package model;

public class Game {

    private Integer id;
    private String name;
    private Team turn;

    public Game(String name, Team turn) {
        this.name = name;
        this.turn = turn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getTurn() {
        return turn;
    }

    public void nextTurn() {
        turn = turn.nextTurn();
    }
}
