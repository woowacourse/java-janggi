package domain.fake;

import domain.chessPiece.Cannon;
import domain.chessPiece.Chariot;
import domain.chessPiece.ChessPiece;
import domain.chessPiece.Elephant;
import domain.chessPiece.Guard;
import domain.chessPiece.Horse;
import domain.chessPiece.Pawn;
import domain.position.ChessPiecePositionsGenerator;
import java.util.ArrayList;
import java.util.List;

public class InitialChessPiecePositionsWithOutKingGenerator implements ChessPiecePositionsGenerator {
    @Override
    public List<ChessPiece> generate() {
        final List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(Cannon.initPieces());
        chessPieces.addAll(Chariot.initPieces());
        chessPieces.addAll(Elephant.initPieces());
        chessPieces.addAll(Horse.initPieces());
        chessPieces.addAll(Pawn.initPieces());
        chessPieces.addAll(Guard.initPieces());
        return chessPieces;
    }
}
