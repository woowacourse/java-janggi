package janggi.board;

import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.piece.PieceSymbol;
import java.util.Map;
import java.util.stream.Collectors;

public final class Judge {

    private static final int INITIAL_GENERAL_COUNT = 2;
    private static final double HAN_CORRECTION = 1.5;

    public boolean isGameOver(Board board) {
        return board.getPlacedPieces()
                .values()
                .stream()
                .filter(piece -> piece.getPieceSymbol() == PieceSymbol.GENERAL)
                .count() < INITIAL_GENERAL_COUNT;
    }

    public Map<Camp, Double> calculateScore(Board board) {
        return applyCorrection(calculateScoreByCamp(board));
    }

    private Map<Camp, Double> calculateScoreByCamp(Board board) {
        return board.getPlacedPieces()
                .values()
                .stream()
                .collect(Collectors.groupingBy(
                        Piece::getCamp,
                        Collectors.summingDouble(Piece::getPoint)
                ));
    }

    private Map<Camp, Double> applyCorrection(Map<Camp, Double> scores) {
        scores.merge(Camp.HAN, HAN_CORRECTION, Double::sum);
        return scores;
    }
}
