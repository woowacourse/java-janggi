package domain.score;

import domain.piece.TeamColor;

public record PieceScore(double cho, double han) {

    public double scoreOf(TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return cho;
        }
        return han;
    }
}
