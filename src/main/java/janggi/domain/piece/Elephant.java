package janggi.domain.piece;

import janggi.domain.Dynasty;
import java.util.Objects;

public class Elephant implements Piece{

    private final Dynasty dynasty;

    public Elephant(Dynasty dynasty) {
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
        Elephant elephant = (Elephant) o;
        return dynasty == elephant.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }
}
