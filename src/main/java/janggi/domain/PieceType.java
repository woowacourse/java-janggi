package janggi.domain;

import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.Movement;
import janggi.domain.movement.pathless.*;
import janggi.domain.movement.path.*;

public enum PieceType {
    BYEONG("병", 2, new ByeongMovement()),
    JOL("졸", 2, new JolMovement()),
    GOONG("궁", 0, new InCastleCrossMovement()),
    SA("사", 3, new InCastleCrossMovement()),
    CHA("차", 13, new ChaMovement()),
    MA("마", 5, new MaMovement()),
    PO("포", 7, new PoMovement()),
    SANG("상", 3, new SangMovement());

    private final String name;
    private final int score;
    private final Movement movement;

    PieceType(String name, int score, Movement movement) {
        this.name = name;
        this.score = score;
        this.movement = movement;
    }

    public boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher) {
        return movement.canMove(departure, arrival, pieceSearcher);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
