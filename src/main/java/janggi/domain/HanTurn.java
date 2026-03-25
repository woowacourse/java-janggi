package janggi.domain;

public class HanTurn implements GameStatus {

    private Team team;

    public HanTurn() {
        this.team = Team.HAN;
    }

    @Override
    public GameStatus move(Point from, Point to) {
        return new ChoTurn();
    }
}
