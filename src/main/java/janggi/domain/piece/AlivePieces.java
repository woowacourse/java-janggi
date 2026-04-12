package janggi.domain.piece;

import janggi.domain.Side;
import java.util.List;

public class AlivePieces {

    private static final int DEFAULT_NUMBER_OF_GUNG= 2;

    private final List<Piece> pieces;

    private AlivePieces(List<Piece> pieces) {
        this.pieces = pieces.stream()
                .filter(piece -> !piece.isEmpty())
                .toList();
    }

    public static AlivePieces from(List<Piece> pieces) {
        return new AlivePieces(pieces);
    }

    public double calculateScoreSum(Side side) {
        double totalScore = 0;
        totalScore += addScoreOfSameSide(pieces, side);
        return totalScore;
    }

    private int addScoreOfSameSide(List<Piece> pieces, Side side) {
        return pieces.stream()
                .filter(piece -> piece.isSameSide(side))
                .map(Piece::getPieceType)
                .mapToInt(PieceType::getScore)
                .sum();
    }

    public boolean isEveryGungAlive() {
        long gungCount = pieces.stream()
                .filter(Piece::isGung)
                .count();
        return gungCount == DEFAULT_NUMBER_OF_GUNG;
    }
}
