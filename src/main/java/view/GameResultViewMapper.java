package view;

import domain.game.GameResult;
import domain.pieces.Side;
import view.dto.GameResultDto;

public class GameResultViewMapper {

    public GameResultDto map(GameResult gameResult) {
        return new GameResultDto(gameResult.isEnded(), winnerName(gameResult.winner()));
    }

    private String winnerName(Side winner) {
        if (winner == null) {
            return "";
        }
        if (winner.isCho()) {
            return "초";
        }
        return "한";
    }
}
