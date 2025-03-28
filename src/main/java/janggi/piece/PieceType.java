package janggi.piece;

import janggi.starategy.ChaStrategy;
import janggi.starategy.GungStrategy;
import janggi.starategy.JolStrategy;
import janggi.starategy.MaStrategy;
import janggi.starategy.MoveStrategy;
import janggi.starategy.PoStrategy;
import janggi.starategy.SaStrategy;
import janggi.starategy.SangStrategy;
import java.util.List;

public enum PieceType {
    GUNG("궁", 0, 1, List.of(4), new GungStrategy()),
    SA("사", 3, 0, List.of(3, 5), new SaStrategy()),
    CHA("차", 13, 0, List.of(0, 8), new ChaStrategy()),
    PO("포", 7, 2, List.of(1, 7), new PoStrategy()),
    MA("마", 5, 0, List.of(1, 7), new MaStrategy()),
    SANG("상", 3, 0, List.of(2, 6), new SangStrategy()),
    JOL("졸", 2, 3, List.of(0, 2, 4, 6, 8), new JolStrategy());
    private final String name;
    private final int score;
    private final int height;
    private final List<Integer> defaultXPositions;
    private final MoveStrategy moveStrategy;

    PieceType(String name, int score, int height, List<Integer> defaultXPositions, MoveStrategy moveStrategy) {
        this.name = name;
        this.score = score;
        this.height = height;
        this.defaultXPositions = defaultXPositions;
        this.moveStrategy = moveStrategy;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public List<Integer> getDefaultXPositions() {
        return defaultXPositions;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}
