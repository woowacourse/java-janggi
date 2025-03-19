package janggi.movement;

import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Direction {

    private final int offsetX;
    private final int offsetY;

    Direction(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Position plusOffsetToPosition(Position position) {
        return position.applyOffsetToPosition(offsetX, offsetY);
    }

    public List<Position> testPlusOffset(Position currentPosition) {
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        List<Position> possiblePositions = new ArrayList<>();
        for (int i = 0; i <= offsetX; i++) {
            possiblePositions.add(new Position(x,i));
        }
        for (int i = 0; i < offsetY; i++) {
            possiblePositions.add(new Position(i,y));
        }
        return possiblePositions;
    }

    public int getOffsetX(){
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
