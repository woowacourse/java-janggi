package domain.place.piece;

import domain.place.moveStrategy.Direction;
import java.util.List;

public enum Side {
    CHO("C", 10, -1, List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT)),
    HAN("H", 1, 1, List.of(Direction.TOP, Direction.LEFT, Direction.RIGHT));

    private final String name;
    private final int startLine;
    private final int setupDirection; // 초기화용
    private final List<Direction> soldierDirections; // 이동용

    Side(String name, int startLine, int setupDirection, List<Direction> soldierDirections) {
        this.name = name;
        this.startLine = startLine;
        this.setupDirection = setupDirection;
        this.soldierDirections = soldierDirections;
    }

    public String getName() {
        return name;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getSetupDirection() {
        return setupDirection;
    }

    public List<Direction> getSoldierDirections() {
        return soldierDirections;
    }
}
