package view.mapper;

import domain.game.GameResult;
import domain.game.GameScore;
import domain.pieces.Side;
import view.dto.GameScoreResultDto;

public class GameScoreResultViewMapper {

    public GameScoreResultDto map(GameResult gameResult, GameScore gameScore) {
        return new GameScoreResultDto(
                winnerName(gameResult.winner()),
                scoreText(gameScore.choScore().value()),
                scoreText(gameScore.hanScore().value())
        );
    }

    private String winnerName(Side winner) {
        if (winner.isCho()) {
            return "초";
        }
        return "한";
    }

    private String scoreText(double score) {
        return String.format("%.1f", score);
    }
}
