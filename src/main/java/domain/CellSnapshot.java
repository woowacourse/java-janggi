package domain;

import domain.piece.PieceType;

public record CellSnapshot(PieceType type, Side side) {

    public String typeName() {
        return type.name();
    }

    public String sideName() {
        return side.name();
    }
}
