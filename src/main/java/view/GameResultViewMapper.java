package view;

import domain.game.GameResult;
import view.dto.GameResultDto;

public class GameResultViewMapper {

    public GameResultDto map(GameResult gameResult) {
        return new GameResultDto(gameResult.status(), gameResult.winner());
    }
}
