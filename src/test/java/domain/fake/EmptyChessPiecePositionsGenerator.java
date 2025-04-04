package domain.fake;

import domain.chesspiece.ChessPiece;
import domain.position.ChessPiecePositionsGenerator;
import java.util.List;

public class EmptyChessPiecePositionsGenerator implements ChessPiecePositionsGenerator {

    @Override
    public List<ChessPiece> generate() {
        return List.of();
    }
}
