package domain;

import java.util.Map;

public final class ScoreCalculator {

    public TeamScores calculate(Board board) {
        MaterialPoints choTotal = totalForTeam(board, TeamColor.CHO);
        MaterialPoints hanTotal = totalForTeam(board, TeamColor.HAN);
        return TeamScores.of(choTotal, hanTotal);
    }

    private MaterialPoints totalForTeam(Board board, TeamColor teamColor) {
        MaterialPoints base = teamColor.startingScore();
        MaterialPoints material = materialSum(board, teamColor);
        return base.plus(material);
    }

    private MaterialPoints materialSum(Board board, TeamColor teamColor) {
        MaterialPoints sum = MaterialPoints.zero();
        for (Map.Entry<Position, Piece> entry : board.findPiecesByTeam(teamColor)) {
            sum = addPieceMaterial(sum, entry);
        }
        return sum;
    }

    private MaterialPoints addPieceMaterial(MaterialPoints sum, Map.Entry<Position, Piece> entry) {
        Piece piece = entry.getValue();
        return sum.plus(piece.materialPoints());
    }
}
