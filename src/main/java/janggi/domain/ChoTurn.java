package janggi.domain;

public class ChoTurn implements GameStatus{

    private Team team;

    public ChoTurn() {
        this.team = Team.CHO;
    }

    @Override
    public GameStatus move(Point from, Point to) {
        return new HanTurn();
    }
}
