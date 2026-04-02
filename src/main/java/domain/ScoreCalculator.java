package domain;

import domain.board.BoardStatus;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.HashMap;
import java.util.Map;

public class ScoreCalculator {

    private static final Map<PieceType, Integer> scorePerPieceTypeInformation = new HashMap<>() {{
        put(PieceType.CHA, 13);
        put(PieceType.PO, 7);
        put(PieceType.MA, 5);
        put(PieceType.SANG, 3);
        put(PieceType.SA, 3);
        put(PieceType.JOL, 2);
        put(PieceType.BYEONG, 2);
        put(PieceType.JANG, 0);
    }};


    public JanggiScore calculate(BoardStatus boardStatus) {
        double choScore = 0;
        double hanScore = 1.5;

        for (Piece piece : boardStatus.status().values()) {
            if (piece.getTeam() == Team.CHO) {
                choScore += scorePerPieceTypeInformation.getOrDefault(piece.getPieceType(), 0);
            }
            if (piece.getTeam() == Team.HAN) {
                hanScore += scorePerPieceTypeInformation.getOrDefault(piece.getPieceType(), 0);
            }
        }
        return new JanggiScore(choScore, hanScore);
    }
}
