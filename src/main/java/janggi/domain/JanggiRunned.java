package janggi.domain;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;
import java.util.Objects;

public class JanggiRunned implements JanggiStatus {

    private final Dynasty currentTurnDynasty;
    private final JanggiBoard janggiBoard;

    public JanggiRunned(Dynasty currentTurnDynasty, JanggiBoard janggiBoard) {
        this.currentTurnDynasty = currentTurnDynasty;
        this.janggiBoard = janggiBoard;
    }

    @Override
    public JanggiStatus move(Point from, Point to) {
        janggiBoard.move(currentTurnDynasty, from, to);
        if (janggiBoard.isDeadKing(currentTurnDynasty.opposite())) {
            return new JanggiEnded(currentTurnDynasty, janggiBoard);
        }
        return new JanggiRunned(currentTurnDynasty.opposite(), janggiBoard);
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

    @Override
    public JanggiBoard janggiBoard() {
        return janggiBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JanggiRunned that = (JanggiRunned) o;
        return currentTurnDynasty == that.currentTurnDynasty && Objects.equals(janggiBoard, that.janggiBoard);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(currentTurnDynasty);
        result = 31 * result + Objects.hashCode(janggiBoard);
        return result;
    }
}
