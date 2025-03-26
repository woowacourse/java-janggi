package domain;

import domain.board.PieceSearcher;
import domain.movement.Movement;
import domain.movement.pathless.*;
import domain.movement.path.*;

public enum PieceType {
    BYEONG("병", 1, new ByeongMovement()),
    JOL("졸", 1, new JolMovement()),
    GOONG("궁", 1, new GoongMovement()),
    SA("사", 1, new SaMovement()),
    CHA("차", 1, new ChaMovement()),
    MA("마", 1, new MaMovement()),
    PO("포", 1, new PoMovement()),
    SANG("상", 1, new SangMovement());

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
}
