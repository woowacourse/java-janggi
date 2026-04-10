package janggi.domain.janggiGame;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoard {
    private final Map<PieceType, Double> POINTS = new HashMap<>() {
        {
            put(PieceType.TANK, 12.0);
            put(PieceType.CANNON, 7.0);
            put(PieceType.HORSE, 5.0);
            put(PieceType.ELEPHANT, 3.0);
            put(PieceType.ADVISOR, 3.0);
            put(PieceType.SOLDIER, 2.0);
        }
    };
    private final double hanScore;
    private final double choScore;

    public ScoreBoard(List<Piece> hanPieces, List<Piece> choPieces) {
        this.hanScore = calculateScore(hanPieces) + 1.5;
        this.choScore = calculateScore(choPieces);
    }

    public Team winner() {
        if (hanScore > choScore) {
            return Team.HAN;
        }

        return Team.CHO;
    }

    private double calculateScore(List<Piece> pieces) {
        return pieces.stream()
                .map(Piece::pieceType)
                .mapToDouble(type -> POINTS.getOrDefault(type, 0.0))
                .sum();
    }
}
