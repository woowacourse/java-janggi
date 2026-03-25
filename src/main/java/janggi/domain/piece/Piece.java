package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;

public record Piece(
        Dynasty dynasty,
        MoveStrategy moveStrategy
) {

    public boolean isSameDynasty(Dynasty dynasty) {
        return this.dynasty.equals(dynasty);
    }

}
