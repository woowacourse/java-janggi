package domain.place.piece;

import domain.place.moveStrategy.ChoSoldierMoveStrategy;
import domain.place.moveStrategy.HanSoldierMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;

public enum Side {
    CHO("C", 10, -1, new ChoSoldierMoveStrategy()),
    HAN("H", 1, 1, new HanSoldierMoveStrategy());

    private final String name;
    private final int startLine;
    private final int direction;
    private final MoveStrategy soliderMoveStrategy;

    Side(String name, int startLine, int direction, MoveStrategy soliderMoveStrategy) {
        this.name = name;
        this.startLine = startLine;
        this.direction = direction;
        this.soliderMoveStrategy = soliderMoveStrategy;
    }

    public String getName() {
        return name;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getDirection() {
        return direction;
    }

    public MoveStrategy getSoliderMoveStrategy(){
        return soliderMoveStrategy;
    }
}