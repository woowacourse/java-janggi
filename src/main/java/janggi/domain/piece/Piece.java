package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;

public record Piece (
        Dynasty dynasty,
        MoveStrategy moveStrategy
) {

}
