package janggi.team;

import janggi.piece.Piece;
import janggi.piece.PieceStatus;
import java.util.List;

public class TeamScore {
    private double score;

    public void calculateTeamScore(TeamName teamName, List<Piece> teamPieces) {
        if (teamName.equals(TeamName.CHO)) {
            this.score = 0;
        }
        if (teamName.equals(TeamName.HAN)) {
            this.score = 1.5;
        }
        this.score += teamPieces.stream()
                .filter(piece -> piece.matchStatus(PieceStatus.ALIVE))
                .mapToDouble(Piece::getScore).sum();
    }

    public double getScore() {
        return this.score;
    }
}
