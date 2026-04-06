package domain;

import java.util.Map;

public final class ScoreCalculator {

    public TeamScores calculate(GameSnapshot snapshot) {
        Map<Position, Piece> placedPieces = snapshot.pieces();
        MaterialPoints choTotal = totalForTeam(placedPieces, TeamColor.CHO);
        MaterialPoints hanTotal = totalForTeam(placedPieces, TeamColor.HAN);
        return TeamScores.of(choTotal, hanTotal);
    }

    private MaterialPoints totalForTeam(Map<Position, Piece> pieces, TeamColor teamColor) {
        MaterialPoints base = teamColor.startingScore();
        MaterialPoints material = materialSumForTeam(pieces, teamColor);
        return base.plus(material);
    }

    private MaterialPoints materialSumForTeam(Map<Position, Piece> pieces, TeamColor teamColor) {
        MaterialPoints sum = MaterialPoints.zero();
        for (Piece piece : pieces.values()) {
            if (piece.isOnTeam(teamColor)) {
                sum = sum.plus(piece.materialPoints());
            }
        }
        return sum;
    }
}
