package view;

import domain.game.GameResult;
import domain.game.GameScore;
import view.dto.GameScoreResultDto;

public class GameScoreResultViewMapper {

    public GameScoreResultDto map(GameResult gameResult, GameScore gameScore) {
        return new GameScoreResultDto(
                gameResult.winner(),
                gameScore.choScore().value(),
                gameScore.hanScore().value()
        );
    }
}
