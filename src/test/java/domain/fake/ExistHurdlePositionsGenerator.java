package domain.fake;

import domain.chessPiece.ChessPiece;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositionsGenerator;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;

public class ExistHurdlePositionsGenerator implements ChessPiecePositionsGenerator {

    @Override
    public List<ChessPiece> generate() {
        return List.of(
                new Pawn(new ChessPosition(3, 4), ChessTeam.RED),
                new Pawn(new ChessPosition(5, 2), ChessTeam.BLUE)
        );
    }
}
