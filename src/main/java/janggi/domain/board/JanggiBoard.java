package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.domain.piece.gererator.KnightElephantSetting;

import java.util.ArrayList;
import java.util.List;

public class JanggiBoard {

    private final List<Piece> pieces;
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

        pieces = allPieces;
        turn = Side.getFirstTurn();
    }

    public void move(int x, int y, int destinationX, int destinationY) {
        Position source = new Position(x, y);
        Piece sourcePiece = findPieceByPosition(source);
        List<Piece> existingPieces = getAllPiecesExceptSourcePiece(sourcePiece);
        Position destination = new Position(destinationX, destinationY);

        sourcePiece.move(existingPieces, destination, turn);
        turn = Side.opposite(turn);
    }

    public Piece findPieceByPosition(Position position) {
        return pieces.stream()
            .filter(piece -> piece.isSamePosition(position))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다."));
    }

    private List<Piece> getAllPiecesExceptSourcePiece(Piece sourcePiece) {
        return pieces.stream()
            .filter(piece -> !piece.equals(sourcePiece))
            .toList();
    }

    public boolean isEnd() {
        return pieces.stream()
                .filter(Piece::isKing)
                .count() != 2;
    }

    public Side getWinner() {
        return pieces.stream()
                .filter(Piece::isKing)
                .map(Piece::getSide)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
    }
}
