package janggi.domain.piece;

import janggi.domain.Dynasty;
import java.util.Objects;

public class Rook implements Piece {

    private final Dynasty dynasty;

    public Rook(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isSameDynasty(Dynasty dynasty) {
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
        Rook rook = (Rook) o;
        return dynasty == rook.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }
}
