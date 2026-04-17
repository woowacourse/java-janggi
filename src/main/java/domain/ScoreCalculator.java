package domain;

import domain.dto.JanggiBoardDto;
import domain.dto.PieceDto;
import domain.piece.PieceType;
import java.util.List;

public class ScoreCalculator {
    private static final double CHO_DEFAULT_POINT = 0.0;
    private static final double HAN_DEFAULT_POINT = 1.5;

    public double calculateScore(JanggiBoardDto janggiBoardDto, Team team) {
        double totalScore = calculateBaseScore(team);
        List<PieceDto> pieceDtos = janggiBoardDto.getPieces();

        for (PieceDto pieceDto : pieceDtos) {
            if (pieceDto.team() == team) {
                totalScore += getPiecePoint(pieceDto.pieceType());
            }
        }

        return totalScore;
    }

    private double calculateBaseScore(Team team) {
        if (team == Team.HAN) {
            return HAN_DEFAULT_POINT;
        }
        return CHO_DEFAULT_POINT;
    }

    private double getPiecePoint(PieceType pieceType) {
        if (pieceType == PieceType.CAR) {
            return 13.0;
        }
        if (pieceType == PieceType.CANNON) {
            return 7.0;
        }
        if (pieceType == PieceType.HORSE) {
            return 5.0;
        }
        if (pieceType == PieceType.ELEPHANT) {
            return 3.0;
        }
        if (pieceType == PieceType.GUARD || pieceType == PieceType.PAWN) {
            return 2.0;
        }
        return 0.0;
    }
}
