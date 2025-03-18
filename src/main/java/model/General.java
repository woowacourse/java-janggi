package model;

import java.util.List;

public class General extends Piece{

    private General(Team team) {
        super(team);
        initializePosition(team);
    }

    public static General initializePositionFrom(Team team) {
        return new General(team);
    }

    @Override
    protected void initializePosition(Team team) {
        if (team == Team.RED) {
            this.position = new Position(1,4);
            return;
        }
        this.position = new Position(8, 4);
    }

    @Override
    public void up() {
        this.position = position.decreaseColumn(1);
    }

    @Override
    public void left() {
        this.position = position.decreaseRow(1);
    }

    @Override
    public void down() {
        this.position = position.increaseColumn(1);
    }

    @Override
    public void right() {
        this.position = position.increaseRow(1);
    }

    @Override
    public List<Position> calculateMovablePositions() {
        return null;
    }

}
