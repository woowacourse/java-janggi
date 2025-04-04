package domain.position;

import domain.chesspiece.Cannon;
import domain.chesspiece.Chariot;
import domain.chesspiece.ChessPiece;
import domain.chesspiece.Elephant;
import domain.chesspiece.Guard;
import domain.chesspiece.Horse;
import domain.chesspiece.King;
import domain.chesspiece.Pawn;
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
        chessPieces.addAll(King.initPieces());
        chessPieces.addAll(Guard.initPieces());
        return chessPieces;
    }
}
