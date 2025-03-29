package janggi.domain;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;

public class JanggiStarted implements JanggiStatus {

    private static final Dynasty firstTurnDynasty = Dynasty.CHU;

    private final JanggiBoard janggiBoard;

    public JanggiStarted(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    @Override
    public JanggiStatus play(Point from, Point to) {
        janggiBoard.move(Dynasty.HAN, from, to);
        if (janggiBoard.isDeadKing(firstTurnDynasty.opposite())) {
            return new JanggiEnded(firstTurnDynasty, janggiBoard);
        }
        return new JanggiRuned(firstTurnDynasty.opposite(), janggiBoard);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public Dynasty currentTurn() {
        return firstTurnDynasty;
    }

    @Override
    public Dynasty winner() {
        throw new IllegalStateException("우승자가 아직 없습니다.");
    }
}
