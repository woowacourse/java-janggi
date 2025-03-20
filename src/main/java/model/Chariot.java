package model;

import java.util.ArrayList;
import java.util.List;


public class Chariot extends Piece{

    public Chariot(Position position, Team team) {
        super(position, team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        List<List<Position>> positions = new ArrayList<>();
        positions.addAll(findUpDirection());
        positions.addAll(findDownDirection());
        positions.addAll(findLeftDirection());
        positions.addAll(findRightDirection());
        return positions;
    }

    private List<List<Position>> findUpDirection() {
        int count = -1;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfColumn(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=-1; i>=count; i--) {
                positions.add(this.position.changeColumn(i));
            }
            count--;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }

    private List<List<Position>> findDownDirection() {
        int count = 1;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfColumn(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=1; i<=count; i++) {
                positions.add(this.position.changeColumn(i));
            }
            count++;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }

    private List<List<Position>> findLeftDirection() {
        int count = -1;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfRow(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=-1; i>=count; i--) {
                positions.add(this.position.changeRow(i));
            }
            count--;
            tmpPosition.add(positions);
        }
        return tmpPosition;
    }

    private List<List<Position>> findRightDirection() {
        int count = 1;
        List<List<Position>> tmpPosition = new ArrayList<>();
        while (position.canChangeOfRow(count)) {
            List<Position> positions = new ArrayList<>();
            for (int i=1; i<=count; i++) {
                positions.add(this.position.changeRow(i));
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

