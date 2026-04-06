package domain;

import domain.board.BoardStatus;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Map;

public class ScoreCalculator {

    private static final Map<PieceType, Integer> scorePerPieceTypeInformation = Map.of(
            PieceType.CHA, 13,
            PieceType.PO, 7,
            PieceType.MA, 5,
            PieceType.SANG, 3,
            PieceType.SA, 3,
            PieceType.JOL, 2,
            PieceType.BYEONG, 2,
            PieceType.JANG, 0
    );

    public JanggiScore calculate(BoardStatus boardStatus) {
        double choScore = 0;
        double hanScore = 0;

        double hanBonusScore = 1.5;

        for (Piece piece : boardStatus.status().values()) {
            if (piece.getTeam() == Team.CHO) {
                choScore += scorePerPieceTypeInformation.getOrDefault(piece.getPieceType(), 0);
            }
            if (piece.getTeam() == Team.HAN) {
                hanScore += scorePerPieceTypeInformation.getOrDefault(piece.getPieceType(), 0);
            }
        }

        return new JanggiScore(
                choScore,
                hanScore + hanBonusScore
        );
    }
}
