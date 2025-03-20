package model;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece{

    public Cannon(Position position, Team team) {
        super(position, team);
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
        int count = -2;
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
        int count = 2;
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
        int count = -2;
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
        int count = 2;
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
}



