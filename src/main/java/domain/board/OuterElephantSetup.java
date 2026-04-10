package domain.board;

import domain.piece.PieceType;

import java.util.List;

public class OuterElephantSetup implements Setup {
    @Override
    public List<PieceType> arrange() {
        return List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT);
    }
}
