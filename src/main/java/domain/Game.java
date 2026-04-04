package domain;

public class Game {
    private Long id;
    private Team turn;

    public Game() {
        this.turn = Team.CHO;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Team changeTurn() {
        if (this.turn == Team.CHO) {
            return this.turn = Team.HAN;
        }
        return this.turn = Team.HAN;
    }

    public Team turn () {
        return this.turn;
    }

    public Long id () {
        return this.id;
    }
}
