package domain.fake;

import domain.chessPiece.Cannon;
import domain.chessPiece.ChessPiece;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;

public class ExistCannonPositionsGenerator implements ChessPiecePositionsGenerator {

    @Override
    public List<ChessPiece> generate() {
        return List.of(
                new Cannon(ChessTeam.RED,new ChessPosition(6, 4)),
                new Pawn(new ChessPosition(2, 4), ChessTeam.RED),
                new Pawn(new ChessPosition(7,5), ChessTeam.RED),
                new Pawn(new ChessPosition(1, 4), ChessTeam.RED)
        );
    }
}
