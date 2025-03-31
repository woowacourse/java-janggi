package janggi.team;

import janggi.piece.Piece;
import janggi.piece.PieceStatus;
import java.util.List;

public enum TeamScore {
    TEAM_CHO {
        @Override
        public double calculateScore(List<Piece> teamPieces) {
            return CHO_DEFAULT_SCORE + teamPieces.stream()
                    .filter(piece -> piece.matchStatus(PieceStatus.ALIVE))
                    .mapToDouble(Piece::getScore).sum();
        }
    },
    TEAM_HAN {
        @Override
        public double calculateScore(List<Piece> teamPieces) {
            return HAN_DEFAULT_SCORE + teamPieces.stream()
                    .filter(piece -> piece.matchStatus(PieceStatus.ALIVE))
                    .mapToDouble(Piece::getScore).sum();
        }
    };

    private static final double CHO_DEFAULT_SCORE = 0;
    private static final double HAN_DEFAULT_SCORE = 1.5;

    public abstract double calculateScore(List<Piece> teamPieces);

    public static TeamScore from(TeamName teamName) {
        if (teamName.equals(TeamName.CHO)) {
            return TeamScore.TEAM_CHO;
        }
        return TeamScore.TEAM_HAN;
    }
}
