package janggi.piece;

import janggi.strategy.ByungStrategy;
import janggi.strategy.ChaStrategy;
import janggi.strategy.GungStrategy;
import janggi.strategy.JolStrategy;
import janggi.strategy.MaStrategy;
import janggi.strategy.MoveStrategy;
import janggi.strategy.PoStrategy;
import janggi.strategy.SaStrategy;
import janggi.strategy.SangStrategy;

public enum PieceType {
    GUNG("궁", 0, new GungStrategy()),
    SA("사", 3, new SaStrategy()),
    CHA("차", 13, new ChaStrategy()),
    PO("포", 7, new PoStrategy()),
    MA("마", 5, new MaStrategy()),
    SANG("상", 3, new SangStrategy()),
    JOL("졸", 2, new JolStrategy()),
    BYUNG("병", 2, new ByungStrategy());
    private final String name;
    private final int score;
    private final MoveStrategy moveStrategy;

    PieceType(String name, int score, MoveStrategy moveStrategy) {
        this.name = name;
        this.score = score;
        this.moveStrategy = moveStrategy;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}
