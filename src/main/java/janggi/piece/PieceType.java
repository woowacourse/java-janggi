package janggi.piece;

import janggi.starategy.ByungStrategy;
import janggi.starategy.ChaStrategy;
import janggi.starategy.GungStrategy;
import janggi.starategy.JolStrategy;
import janggi.starategy.MaStrategy;
import janggi.starategy.MoveStrategy;
import janggi.starategy.PoStrategy;
import janggi.starategy.SaStrategy;
import janggi.starategy.SangStrategy;

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
