package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.domain.piece.generator.KnightElephantSetting;

import java.util.ArrayList;
import java.util.List;

public class JanggiBoard {

    private final Pieces pieces;
    private Side turn;

    public JanggiBoard(
            HanPieceGenerator hanPieceGenerator,
            ChoPieceGenerator choPieceGenerator,
            KnightElephantSetting hanKnightElephantSetting,
            KnightElephantSetting choKnightElephantSetting
    ) {
        List<Piece> hanPieces = hanPieceGenerator.generate(hanKnightElephantSetting);
        List<Piece> choPieces = choPieceGenerator.generate(choKnightElephantSetting);
        List<Piece> allPieces = new ArrayList<Piece>(hanPieces);
        allPieces.addAll(choPieces);

        pieces = new Pieces(allPieces);
        turn = Side.getFirstTurn();
    }

    public void move(Position start, Position destination) {
        Piece sourcePiece = findPieceByPosition(start);
        List<Piece> allPiecesExceptSourcePiece = pieces.getAllPiecesExcept(sourcePiece);

        sourcePiece.move(allPiecesExceptSourcePiece, destination, turn);
        pieces.killEnemyPieceIfPresent(destination, turn);
        turn = Side.opposite(turn);
    }

    public Piece findPieceByPosition(Position position) {
        return pieces.findPieceByPosition(position);
    }

    public boolean isEnd() {
        return pieces.isEnd();
    }

    public Side getWinner() {
        return pieces.getWinner();
    }
}
