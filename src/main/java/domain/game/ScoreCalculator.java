package domain.game;

import domain.board.BoardState;
import domain.board.IntersectionState;
import domain.piece.PieceType;
import domain.team.Team;

public class ScoreCalculator {
    private static final double HAN_START_BONUS = 1.5;

    public GameScore calculate(BoardState boardState) {
        final double choScore = boardState.getBoardState().stream()
                .filter(state -> state.getTeam() == Team.CHO)
                .map(IntersectionState::getPieceType)
                .mapToInt(PieceType::getScore)
                .sum();

        final double hanScore = boardState.getBoardState().stream()
                .filter(state -> state.getTeam() == Team.HAN)
                .map(IntersectionState::getPieceType)
                .mapToInt(PieceType::getScore)
                .sum()
                + HAN_START_BONUS;

        return new GameScore(choScore, hanScore);
    }
}
