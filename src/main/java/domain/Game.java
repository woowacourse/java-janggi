package domain;

public class Game {
    private Long id;
    private Team turn;
    private Board board;

    public Game(Board board) {
        this.turn = Team.CHO;
        this.board = board;
    }

    public Game(Long id, Team turn, Board board) {
        this.id = id;
        this.turn = turn;
        this.board = board;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public void changeTurn() {
        if (this.turn == Team.CHO) {
            this.turn = Team.HAN;
            return;
        }
        this.turn = Team.CHO;
    }

    public Team turn () {
        return this.turn;
    }

    public Long id () {
        return this.id;
    }

    public Board board () {
        return this.board;
    }
}
