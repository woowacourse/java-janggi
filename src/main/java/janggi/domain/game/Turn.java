package janggi.domain.game;

import java.util.Objects;

public class Turn {
    private Side current;

    // 게임 처음 시작할 때 사용
    public Turn() {
        this.current = Side.CHO;
    }

    // DB에서 게임 불러온 상태로 복구할 때 사용
    public Turn(Side side) {
        this.current = side;
    }

    public void switchTurn() {
        this.current = current.opposite();
    }

    public boolean isCurrent(Side side) {
        return this.current == side;
    }

    public Side getSide() {
        return current;
    }
}
