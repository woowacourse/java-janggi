package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Objects;

public class Elephant implements Piece {

    private final Dynasty dynasty;

    public Elephant(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
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
        Elephant elephant = (Elephant) o;
        return dynasty == elephant.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }
}
