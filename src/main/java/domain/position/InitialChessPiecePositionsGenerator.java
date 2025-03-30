package domain.position;

import domain.chessPiece.Cannon;
import domain.chessPiece.Chariot;
import domain.chessPiece.ChessPiece;
import domain.chessPiece.Elephant;
import domain.chessPiece.Horse;
import domain.chessPiece.Pawn;
import java.util.ArrayList;
import java.util.List;

public class InitialChessPiecePositionsGenerator implements ChessPiecePositionsGenerator {
    @Override
    public List<ChessPiece> generate() {
        final List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(Cannon.initPieces());
        chessPieces.addAll(Chariot.initPieces());
        chessPieces.addAll(Elephant.initPieces());
        chessPieces.addAll(Horse.initPieces());
        chessPieces.addAll(Pawn.initPieces());
        return chessPieces;
    }
}
