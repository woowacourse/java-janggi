package domain.fake;

import domain.chesspiece.ChessPiece;
import domain.chesspiece.Pawn;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;

public class ExistHurdlePositionsGenerator implements ChessPiecePositionsGenerator {

    @Override
    public List<ChessPiece> generate() {
        return List.of(
                new Pawn(ChessTeam.RED, new ChessPosition(3, 4)),
                new Pawn(ChessTeam.BLUE, new ChessPosition(5, 2))
        );
    }
}
