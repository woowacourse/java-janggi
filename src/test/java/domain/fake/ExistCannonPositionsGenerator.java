package domain.fake;

import domain.chesspiece.Cannon;
import domain.chesspiece.ChessPiece;
import domain.chesspiece.Pawn;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;

public class ExistCannonPositionsGenerator implements ChessPiecePositionsGenerator {

    @Override
    public List<ChessPiece> generate() {
        return List.of(
                new Cannon(ChessTeam.RED,new ChessPosition(6, 4)),
                new Pawn(ChessTeam.RED, new ChessPosition(2, 4)),
                new Pawn(ChessTeam.RED, new ChessPosition(7,5)),
                new Pawn(ChessTeam.RED, new ChessPosition(1, 4))
        );
    }
}
