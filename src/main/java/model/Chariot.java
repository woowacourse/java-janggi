package model;

import java.util.ArrayList;
import java.util.List;


public class Chariot extends Piece{

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<List<Position>> calculateAllDirection(Position position) {
        List<List<Position>> positions = new ArrayList<>();
        positions.addAll(findUpDirection(position));
        positions.addAll(findDownDirection(position));
        positions.addAll(findLeftDirection(position));
        positions.addAll(findRightDirection(position));
        return positions;
    }

    private List<List<Position>> findUpDirection(Position position) {
        int count = -1;
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
        int count = 1;
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
        int count = -1;
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
        int count = 1;
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

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }
}

