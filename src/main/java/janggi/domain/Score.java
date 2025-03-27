package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.List;

public class Score {
    private static final double ADDITIONAL_POINT_FOR_RED = 1.5;
    private final List<Piece> pieces;

    public Score(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public double calculateTeamScore(Team team) {
        int teamPoint = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .map(Piece::getScore)
                .mapToInt(i -> i)
                .sum();
        if (team == Team.RED) {
            return teamPoint + ADDITIONAL_POINT_FOR_RED;
        }
        return teamPoint;
    }
}
