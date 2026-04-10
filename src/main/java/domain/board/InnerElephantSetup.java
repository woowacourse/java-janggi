package domain.board;

import domain.piece.PieceType;

import java.util.List;

public class InnerElephantSetup implements Setup{
    @Override
    public List<PieceType> arrange() {
        return List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE);
    }
}
