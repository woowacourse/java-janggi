package domain.game;

import domain.board.Pieces;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Arrays;

public record GameResult(Team winner, int choScore, int hanScore) {
    public static GameResult from(Pieces snapshot, Pieces current, Team winner) {
        int choScore = scoreFromCapturedPieces(snapshot, current, Team.HAN);
        int hanScore = scoreFromCapturedPieces(snapshot, current, Team.CHO);
        return new GameResult(winner, choScore, hanScore);
    }

    private static int scoreFromCapturedPieces(Pieces snapshot, Pieces current, Team defeatedTeam) {
        return Arrays.stream(PieceType.values())
                .mapToInt(pieceType -> capturedScore(snapshot, current, defeatedTeam, pieceType))
                .sum();
    }

    private static int capturedScore(Pieces snapshot, Pieces current, Team defeatedTeam, PieceType pieceType) {
        int removedCount = snapshot.count(defeatedTeam, pieceType) - current.count(defeatedTeam, pieceType);
        return removedCount * pieceType.score();
    }
}
