package domain.place.piece;

import domain.place.moveStrategy.Direction;
import java.util.List;

public enum Side {
    CHO("C", 10, -1),
    HAN("H", 1, 1);

    private final String name;
    private final int startLine;
    private final int setupDirection; // 초기화용
    Side(String name, int startLine, int setupDirection) {
        this.name = name;
        this.startLine = startLine;
        this.setupDirection = setupDirection;
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

}
