package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Objects;

public class Cannon implements Piece {

    private final Dynasty dynasty;

    public Cannon(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isSameDynasty(Dynasty dynasty) {
        return false;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cannon cannon = (Cannon) o;
        return dynasty == cannon.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }
}
