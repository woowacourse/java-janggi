package janggi.domain.gamestatus;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;
import java.util.Objects;

public class GameEnded implements GameStatus {

    private final Dynasty winnerDynasty;
    private final JanggiBoard janggiBoard;

    public GameEnded(Dynasty winnerDynasty, JanggiBoard janggiBoard) {
        this.winnerDynasty = winnerDynasty;
        this.janggiBoard = janggiBoard;
    }

    @Override
    public GameStatus move(Point from, Point to) {
        throw new IllegalStateException("게임이 끝났습니다.");
    }

    @Override
    public boolean isEndGame() {
        return true;
    }

    @Override
    public Dynasty currentTurn() {
        throw new IllegalStateException("게임이 종료되어 현재 턴이 없습니다.");
    }

    @Override
    public Dynasty winner() {
        return winnerDynasty;
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

        GameEnded that = (GameEnded) o;
        return winnerDynasty == that.winnerDynasty && Objects.equals(janggiBoard, that.janggiBoard);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(winnerDynasty);
        result = 31 * result + Objects.hashCode(janggiBoard);
        return result;
    }
}
