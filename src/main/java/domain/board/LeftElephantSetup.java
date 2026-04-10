package domain.board;

import domain.piece.PieceType;

import java.util.List;

public class LeftElephantSetup implements Setup {
    @Override
    public List<PieceType> arrange() {
        return List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE);
    }
}
