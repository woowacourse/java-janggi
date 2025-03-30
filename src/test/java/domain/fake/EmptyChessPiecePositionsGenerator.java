package domain.fake;

import domain.chessPiece.ChessPiece;
import domain.position.ChessPiecePositionsGenerator;
import java.util.List;

public class EmptyChessPiecePositionsGenerator implements ChessPiecePositionsGenerator {

    @Override
    public List<ChessPiece> generate() {
        return List.of();
    }
}
