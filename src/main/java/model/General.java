package model;

import java.util.Collections;
import java.util.List;

public class General extends Piece {

    public General(Position position, Team team) {
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
            return "漢";
        }
        return "초";
    }
}
