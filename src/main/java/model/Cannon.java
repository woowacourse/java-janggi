package model;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return List.of();
    }

/*
    private List<List<Position>> findUpDirection(Position position) {
        int count = -2;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfColumn(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=-1; i>=count; i--) {
                positions.add(position.changeColumn(i));
            }
            count--;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }

    private List<List<Position>> findDownDirection(Position position) {
        int count = 2;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfColumn(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=1; i<=count; i++) {
                positions.add(position.changeColumn(i));
            }
            count++;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }

    private List<List<Position>> findLeftDirection(Position position) {
        int count = -2;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfRow(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=-1; i>=count; i--) {
                positions.add(position.changeRow(i));
            }
            count--;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }

    private List<List<Position>> findRightDirection(Position position) {
        int count = 2;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfRow(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=1; i<=count; i++) {
                positions.add(position.changeRow(i));
            }
            count++;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }


 */
    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "包";
        }
        return "포";
    }
}





