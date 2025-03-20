package model;

import java.util.Collections;
import java.util.List;

public class Guard extends Piece {

    public Guard(Position position, Team team) {
        super(position, team);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of(
            findUpDirection(),
            findDownDirection(),
            findLeftDirection(),
            findRightDirection());
    }

    private List<Position> findUpDirection() {
        if (position.canChangeOfColumn(-1)) {
            return List.of(position.changeColumn(-1));
        }
        return Collections.emptyList();
    }

    private List<Position> findDownDirection() {
        if (position.canChangeOfColumn(1)) {
            return List.of(position.changeColumn(1));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftDirection() {
        if (position.canChangeOfRow(-1)) {
            return List.of(position.changeRow(-1));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightDirection() {
        if (position.canChangeOfRow(1)) {
            return List.of(position.changeRow(1));
        }
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "士";
        }
        return "사";
    }
}
