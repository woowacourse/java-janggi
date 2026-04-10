package domain;

import java.util.List;

public final class ScoreCalculator {

    public TeamScores calculate(List<Piece> choPieces, List<Piece> hanPieces) {
        MaterialPoints choTotal = totalForTeam(TeamColor.CHO, choPieces);
        MaterialPoints hanTotal = totalForTeam(TeamColor.HAN, hanPieces);
        return TeamScores.of(choTotal, hanTotal);
    }

    private MaterialPoints totalForTeam(TeamColor teamColor, List<Piece> pieces) {
        MaterialPoints base = teamColor.startingScore();
        MaterialPoints material = sumMaterial(pieces);
        return base.plus(material);
    }

    private MaterialPoints sumMaterial(List<Piece> pieces) {
        MaterialPoints sum = MaterialPoints.zero();
        for (Piece piece : pieces) {
            sum = sum.plus(piece.materialPoints());
        }
        return sum;
    }
}
