package domain;

import domain.piece.PieceColor;

public record Player(
        String name,
        PieceColor color
) {

}
