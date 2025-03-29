package janggi.domain;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;

public class JanggiRuned implements JanggiStatus {

    private final Dynasty currentTurnDynasty;
    private final JanggiBoard janggiBoard;

    public JanggiRuned(Dynasty currentTurnDynasty, JanggiBoard janggiBoard) {
        this.currentTurnDynasty = currentTurnDynasty;
        this.janggiBoard = janggiBoard;
    }

    @Override
    public JanggiStatus play(Point from, Point to) {
        janggiBoard.move(currentTurnDynasty, from, to);
        if (janggiBoard.isDeadKing(currentTurnDynasty.opposite())) {
            return new JanggiEnded(currentTurnDynasty, janggiBoard);
        }
        return new JanggiRuned(currentTurnDynasty.opposite(), janggiBoard);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public Dynasty currentTurn() {
        return currentTurnDynasty;
    }

    @Override
    public Dynasty winner() {
        throw new IllegalStateException("우승자가 아직 없습니다.");
    }
}
