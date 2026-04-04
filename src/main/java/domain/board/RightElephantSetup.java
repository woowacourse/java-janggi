package domain.board;

import domain.piece.PieceType;

import java.util.List;

public class RightElephantSetup implements Setup {
    @Override
    public List<PieceType> arrange() {
        return List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT);
    }
}
