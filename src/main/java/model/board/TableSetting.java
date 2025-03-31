package model.board;

import java.util.List;

import model.Position;

public enum TableSetting {
    LEFT(
        List.of(new Position(1, 0), new Position(6, 0)),
        List.of(new Position(2, 0), new Position(7, 0))),
    RIGHT(
    List.of(new Position(2, 0), new Position(7, 0)),
        List.of(new Position(1, 0), new Position(6, 0))),
    INSIDE(
    List.of(new Position(2, 0), new Position(6, 0)),
        List.of(new Position(1, 0), new Position(7, 0))),
    OUTSIDE(
    List.of(new Position(1, 0), new Position(7, 0)),
        List.of(new Position(2, 0), new Position(6, 0))),
    ;

    private final List<Position> elephant;
    private final List<Position> horse;

    TableSetting(List<Position> elephant, List<Position> horse) {
        this.elephant = elephant;
        this.horse = horse;
    }

    public List<Position> getElephant() {
        return elephant;
    }

    public List<Position> getHorse() {
        return horse;
    }
}
